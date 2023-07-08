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
import site.kongdroid.api.constants.MessageConstant;
import site.kongdroid.api.dao.CustomDao;
import site.kongdroid.api.exception.BadRequestException;
import site.kongdroid.api.exception.InternalServerException;
import site.kongdroid.api.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	
    private final CustomDao dao;
	private final CommentServiceImpl commentService;
	private final MemberServiceImpl memberService;
	
    public Map<String, Object> getPosts( Map<String, Object> requestMap,
                                         boolean isDouble ) throws InternalResourceException {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        List<Map<String, Object>> posts = new ArrayList<Map<String,Object>>();
        if(isDouble) {

            val searchMap = (Map<String, Object>) requestMap.get("search");
            if(!searchMap.isEmpty()) {
                if(searchMap.get("page") != null && searchMap.get("size") != null) {
                    searchMap.put("startRow", (Integer) searchMap.get("page") * (Integer) searchMap.get("page"));
                }
            }   
            
            posts = dao.dbDetails("post.getPosts", requestMap);
            if(!posts.isEmpty()) {
                posts.forEach(post -> {
                    Map<String, Object> reqMap = new HashMap<>();
                    reqMap.put("memberSeq", post.get("createdNo"));
                    post.put("memberInfo", memberService.getMembers(reqMap, false));
                    
                    
                    Map<String, Object> resComMap = new HashMap<String, Object>();
                    resComMap.put("postSeq", post.get("postSeq"));
                    post.put("comments", commentService.getComments(resComMap).get("comments"));
                });
            }
            
            responseMap.put("posts", posts);
            responseMap.put("totalCount",
                    Integer.parseInt(String.valueOf(dao.dbDetail("post.getPostsCnt", requestMap).get("cnt"))));
        } else {
            responseMap = dao.dbDetail("post.getPosts", requestMap); 

            if(!responseMap.isEmpty()) {
                setPostForViewCount(requestMap);
                
                val reqMap = new HashMap<String, Object>();
                reqMap.put("memberSeq", responseMap.get("createdNo"));
                responseMap.put("memberInfo", memberService.getMembers(reqMap, false));
                
                val resComMap = new HashMap<String, Object>();
                resComMap.put("postSeq", responseMap.get("postSeq"));
                responseMap.put("comments", commentService.getComments(resComMap).get("comments"));
            }
        }
        return responseMap;
    }
    
    public Map<String, Object> handlePost(Map<String, Object> requestMap, boolean isAdd,
                                          boolean isPhysical, String whatAct) throws InternalResourceException {
        val responseMap = new HashMap<String, Object>();
        switch (whatAct) {
            case "regist":
            case "modify":
                if(dao.dbInsert(isAdd ? "post.addPost" : "post.setPost" , requestMap) < 0)
                    throw new InternalServerException(MessageConstant.INVALID_MESSAGE);
                break;
            case "remove":
                val rmvReqMap = new HashMap<String, Object>();
                val handle = ( Map<String, Object> ) requestMap.get("handle");
                rmvReqMap.put("postSeq", requestMap.get("postSeq"));
                val rmvResMap = getPosts(rmvReqMap, false);
                if(!rmvResMap.isEmpty()) {
                    if(rmvResMap.get("createdNo").equals(handle.get("memberSeq"))) {
                        if(isPhysical) {
                            if(dao.dbDelete("post.removePost", requestMap) < 0 )
                                throw new InternalServerException(MessageConstant.INVALID_MESSAGE);
                        } else {
                            handle.put("delYn", "Y");
                            if(dao.dbUpdate("post.setPost", requestMap) < 0)
                                throw new InternalServerException(MessageConstant.INVALID_MESSAGE);
                        }
                    } else {
                        throw new InternalServerException(MessageConstant.INVALID_MESSAGE);
                    }
                } else {
                    throw new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE);
                }
                break;
        }
        responseMap.put("postSeq", requestMap.get("postSeq"));
        return responseMap;
    }

    public void setPostForViewCount(Map<String, Object> requestMap) throws InternalResourceException {
    	if(dao.dbUpdate("post.setPostForViewCount", requestMap) < 0)
            throw new InternalServerException(MessageConstant.INVALID_MESSAGE);
    }
}
