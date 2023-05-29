package site.kongdroid.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.val;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dao.CustomDao;
import site.kongdroid.api.exception.BadRequestException;
import site.kongdroid.api.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    
    private final CustomDao dao;
    private final MemberServiceImpl memberService;

    public Map<String, Object> getComments(Map<String, Object> requestMap) throws InternalResourceException {
        val responseMap = new HashMap<String, Object>();
        val parents = new ArrayList<Map<String, Object>>();
        val chlid = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> comments = dao.dbDetails("comment.getComments", requestMap);
        if (!CollectionUtils.isEmpty(comments)) {
            comments.forEach(comment -> {
                val reqMap = new HashMap<String, Object>();
                reqMap.put("memberSeq", comment.get("createdNo"));
                comment.put("memberInfo", memberService.getMembers(reqMap, false));
                
                if((Integer) comment.get("depsNo") == 1 && (Integer) comment.get("parentsNo") == 0 ) {
                    parents.add(comment);
                }
                
                if((Integer) comment.get("depsNo") >= 2 && (Integer) comment.get("parentsNo") >= 1 ) {
                    chlid.add(comment);
                }
            });
            
            parents.forEach(parent -> {
                val array = new ArrayList<Map<String, Object>>();
                chlid.forEach(chd -> {
                    if((Integer) parent.get("commentSeq") == (Integer) chd.get("parentsNo")) {
                        array.add(chd);
                    }
                });
                parent.put("chlid", array);
            });  
            comments = parents;   
        }
        responseMap.put("comments", comments); 
        responseMap.put("totalCount", parents.size());
        return responseMap;
    }

    public Map<String, Object> handleComment(Map<String, Object> requestMap, boolean isAdd, String whatAct) throws InternalResourceException {
        val responseMap = new HashMap<String, Object>();
        switch (whatAct) {
            case "regist":
            case "modify":
                if(dao.dbInsert(isAdd ? "comment.addComment" : "comment.setComment" , requestMap) < 0) throw new BadRequestException("Invalid Error");
                break;
            case "remove":
                val rmvReqMap = new HashMap<String, Object>();
                val handle = (Map<String, Object>) requestMap.get("handle");

                rmvReqMap.put("commentSeq", requestMap.get("commentSeq"));
                val rmvResMap = dao.dbDetail("comment.getComments", rmvReqMap);

                if(!CollectionUtils.isEmpty(rmvResMap)) {
                    if(rmvResMap.get("createdNo").equals(handle.get("memberSeq"))) {                
                        if(dao.dbDelete("comment.removeComment", requestMap) < 0 ) throw new BadRequestException("Invalid Error");
                    } else {
                        throw new BadRequestException("Invalid Error");
                    }
                } else {
                    new ResourceNotFoundException("Invalid Error");
                }
                break;
        }
        responseMap.put("commentSeq", requestMap.get("commentSeq"));
        return responseMap;
    }
}
