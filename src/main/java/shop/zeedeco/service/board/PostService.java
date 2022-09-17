package shop.zeedeco.service.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
    public Map<String, Object> getPosts(Map<String, Object> requestMap, Integer page, Integer size) throws Exception {
        
    	Map<String, Object> responseMap = new HashMap<>();
        
    	requestMap.put("delYn", "N");
        if(page != null && size != null) {
        	requestMap.put("startRow", page * size);
            requestMap.put("size", size);
        }
        
        List<Map<String, Object>> posts = dao.dbDetails("post.getPosts", requestMap);
        Map<String, Object> listCount = dao.dbDetail("post.getPostsCnt", requestMap);
        Integer totalCount = Integer.parseInt(String.valueOf(listCount.get("cnt")));
        if(!CollectionUtils.isEmpty(posts)) {
            responseMap.put("posts", posts);
            responseMap.put("totalCount", totalCount);
        } else {
        	new ResourceNotFoundException("데이터가 없습니다.");
        }
        return responseMap;
    }

    public Map<String, Object> getPost(int postSeq) throws Exception {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("postSeq", postSeq);
    	// 조회수
    	this.setPostForViewCount(requestMap);
    	Map<String, Object> responseMap = dao.dbDetail("post.getPosts", requestMap);
    	if(CollectionUtils.isEmpty(responseMap)) new ResourceNotFoundException("게시글을 찾을 수 없습니다.");
    	return responseMap;
    }

    public void addPost(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbInsert("post.addPost", requestMap);
    	if(effectRow < 0) throw new BadRequestException("게시글 등록을 실패했습니다.");
    }
    
    public void setPost(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbUpdate("post.setPost", requestMap);
    	if(effectRow < 0) throw new BadRequestException("게시글 수정을 실패했습니다.");
    }
  
    public void setPostForViewCount(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbUpdate("post.setPostForViewCount", requestMap);
    	if(effectRow < 0) throw new BadRequestException("게시글 조회수 수정에 실패했습니다.");
    }
    
    public String removePost ( int postSeq, int mamberSeq, boolean isPhysical ) throws BadRequestException {
    	Map<String, Object> requestMap = new HashMap<>();
    	
    	requestMap.put("postSeq", postSeq);
    	requestMap.put("mamberSeq", mamberSeq);
    	if(!isPhysical) requestMap.put("delYn", "Y");
    	
    	if((!isPhysical ? this.dao.dbUpdate("post.setPost", requestMap) : this.dao.dbDelete("post.removePost", requestMap)) < 0 ) throw new BadRequestException(!isPhysical ? "논리적 삭제를 실패했습니다." : "물리적 삭제를 실패했습니다.");
    	return "게시글이 삭제되었습니다.";
    }

}
