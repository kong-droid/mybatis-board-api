package shop.zeedeco.service.board;

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
public class PostService {
	
    private final CustomDao dao;
	
    
    public Map<String, Object> getPosts( Map<String, Object> requestMap, boolean isDouble ) throws InternalResourceException {
        Map<String, Object> responseMap = !isDouble ? dao.dbDetail("post.getPosts", requestMap) : new HashMap<>();
        if(isDouble) {
            Map<String, Object> searchMap = (Map<String, Object>) requestMap.get("search");         
            if(!CollectionUtils.isEmpty(searchMap)) {
                if(searchMap.get("page") != null && searchMap.get("size") != null) {
                    searchMap.put("startRow", (Integer) searchMap.get("page") * (Integer) searchMap.get("page"));
                }
            }   
            responseMap.put("posts", dao.dbDetails("post.getPosts", requestMap));
            responseMap.put("totalCount", Integer.parseInt(String.valueOf(dao.dbDetail("post.getPostsCnt", requestMap).get("cnt"))));
        } else {
            // 조회수
            this.setPostForViewCount(requestMap);
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
    	if(effectRow < 0) throw new BadRequestException("게시글 조회수 수정에 실패했습니다.");
    }
}
