package site.kongdroid.api.service.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dao.CustomDao;
import site.kongdroid.api.exception.BadRequestException;
import site.kongdroid.api.exception.ResourceNotFoundException;
import site.kongdroid.api.service.MemberService;

@Service
@RequiredArgsConstructor
public class PostService {
	
    private final CustomDao dao;
	private final CommentService commentService;
	private final MemberService memberService;
	
    public Map<String, Object> getPosts( Map<String, Object> requestMap, boolean isDouble ) throws InternalResourceException {
        Map<String, Object> responseMap = new HashMap<>();
        List<Map<String, Object>> posts = new ArrayList<Map<String,Object>>();
        if(isDouble) {
            Map<String, Object> searchMap = (Map<String, Object>) requestMap.get("search");         
            if(!CollectionUtils.isEmpty(searchMap)) {
                if(searchMap.get("page") != null && searchMap.get("size") != null) {
                    searchMap.put("startRow", (Integer) searchMap.get("page") * (Integer) searchMap.get("page"));
                }
            }   
            
            posts = dao.dbDetails("post.getPosts", requestMap);
            if(!CollectionUtils.isEmpty(posts)) {
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
            responseMap.put("totalCount", Integer.parseInt(String.valueOf(dao.dbDetail("post.getPostsCnt", requestMap).get("cnt"))));
        } else {
            responseMap = dao.dbDetail("post.getPosts", requestMap); 
            // ��ȸ��
            if(!CollectionUtils.isEmpty(responseMap)) {
                this.setPostForViewCount(requestMap);
                
                Map<String, Object> reqMap = new HashMap<>();
                reqMap.put("memberSeq", responseMap.get("createdNo"));
                responseMap.put("memberInfo", memberService.getMembers(reqMap, false));
                
                Map<String, Object> resComMap = new HashMap<String, Object>();
                resComMap.put("postSeq", responseMap.get("postSeq"));
                responseMap.put("comments", commentService.getComments(resComMap).get("comments"));
            }
        }
        return responseMap;
    }
    
    public Map<String, Object> handlePost ( Map<String, Object> requestMap, boolean isAdd, boolean isPhysical, String whatAct ) throws InternalResourceException {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        switch (whatAct) {
            case "regist":
            case "modify":
                if(dao.dbInsert(isAdd ? "post.addPost" : "post.setPost" , requestMap) < 0) throw new BadRequestException("Invalid Error");
                break;
            case "remove":
                Map<String, Object> rmvReqMap = new HashMap<>();
                Map<String, Object> handle = ( Map<String, Object> ) requestMap.get("handle");
                rmvReqMap.put("postSeq", requestMap.get("postSeq"));
                Map<String, Object> rmvResMap = this.getPosts(rmvReqMap, false);
                if(!CollectionUtils.isEmpty(rmvResMap)) {
                    if(rmvResMap.get("createdNo").equals(handle.get("memberSeq"))) {
                        if(isPhysical) {
                            if(this.dao.dbDelete("post.removePost", requestMap) < 0 ) throw new BadRequestException("Invalid Error");
                        } else {
                            handle.put("delYn", "Y");
                            if(this.dao.dbUpdate("post.setPost", requestMap) < 0) throw new BadRequestException("Invalid Error");
                        }
                    } else {
                        throw new BadRequestException("Invalid Error");
                    }
                } else {
                    new ResourceNotFoundException("Invalid Error");
                }
                break;
        }
        responseMap.put("postSeq", requestMap.get("postSeq"));
        return responseMap;
    }

    public void setPostForViewCount(Map<String, Object> requestMap) throws InternalResourceException {
    	int effectRow = this.dao.dbUpdate("post.setPostForViewCount", requestMap);
    	if(effectRow < 0) throw new BadRequestException("Invalid Error");
    }
}
