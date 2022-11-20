package shop.zeedeco.service.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;
import shop.zeedeco.dao.CustomDao;
import shop.zeedeco.exception.BadRequestException;
import shop.zeedeco.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final CustomDao dao;
	
    @SuppressWarnings("unchecked")
    public Map<String, Object> getComments( Map<String, Object> requestMap) throws InternalResourceException {
        Map<String, Object> responseMap = new HashMap<>();
        ArrayList<Map<String, Object>> parents = new ArrayList<>();
        ArrayList<Map<String, Object>> chlid = new ArrayList<>();
        List<Map<String, Object>> comments = dao.dbDetails("comment.getComments", requestMap);
        if (!CollectionUtils.isEmpty(comments)) {
            comments.forEach(comment -> {
                if((Integer) comment.get("depsNo") == 1 && (Integer) comment.get("parentsNo") == 0 ) {
                    parents.add(comment);
                }
                
                if((Integer) comment.get("depsNo") >= 2 && (Integer) comment.get("parentsNo") >= 1 ) {
                    chlid.add(comment);
                }
            });
            
            parents.forEach(parent -> {
                ArrayList<Map<String, Object>> array = new ArrayList<>();        
                chlid.forEach(chd -> {
                    if((Integer) parent.get("commentSeq") == (Integer) chd.get("parentsNo")) {
                        array.add(chd);
                    }
                });
                parent.put("chlid", array == null ? null : array);
            });  
            comments = parents;   
        }
        responseMap.put("comments", comments); 
        responseMap.put("totalCount", parents.size());
        return responseMap;
    }

    public Map<String, Object> handleComment ( Map<String, Object> requestMap, boolean isAdd, String whatAct ) throws InternalResourceException {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        switch (whatAct) {
            case "regist":
            case "modify":
                if(dao.dbInsert(isAdd ? "comment.addComment" : "comment.setComment" , requestMap) < 0) throw new BadRequestException("Invalid Error");
                break;
            case "remove":
                Map<String, Object> rmvReqMap = new HashMap<>();
                Map<String, Object> handle = ( Map<String, Object> ) requestMap.get("handle");
                rmvReqMap.put("commentSeq", requestMap.get("commentSeq"));
                Map<String, Object> rmvResMap = dao.dbDetail("comment.getComments", rmvReqMap);
                if(!CollectionUtils.isEmpty(rmvResMap)) {
                    if(rmvResMap.get("createdNo").equals(handle.get("memberSeq"))) {                
                        if(this.dao.dbDelete("comment.removeComment", requestMap) < 0 ) throw new BadRequestException("Invalid Error");
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
