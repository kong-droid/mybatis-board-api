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
        	new ResourceNotFoundException("�����Ͱ� �����ϴ�.");
        }
        return responseMap;
    }

    public Map<String, Object> getPost(int postSeq) throws Exception {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("postSeq", postSeq);
    	// ��ȸ��
    	this.setPostForViewCount(requestMap);
    	Map<String, Object> responseMap = dao.dbDetail("post.getPosts", requestMap);
    	if(CollectionUtils.isEmpty(responseMap)) new ResourceNotFoundException("�Խñ��� ã�� �� �����ϴ�.");
    	return responseMap;
    }

    public void addPost(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbInsert("post.addPost", requestMap);
    	if(effectRow < 0) throw new BadRequestException("�Խñ� ����� �����߽��ϴ�.");
    }
    
    public void setPost(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbUpdate("post.setPost", requestMap);
    	if(effectRow < 0) throw new BadRequestException("�Խñ� ������ �����߽��ϴ�.");
    }
  
    public void setPostForViewCount(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbUpdate("post.setPostForViewCount", requestMap);
    	if(effectRow < 0) throw new BadRequestException("�Խñ� ��ȸ�� ������ �����߽��ϴ�.");
    }
    
    public String removePost ( int postSeq, int mamberSeq, boolean isPhysical ) throws BadRequestException {
    	Map<String, Object> requestMap = new HashMap<>();
    	
    	requestMap.put("postSeq", postSeq);
    	requestMap.put("mamberSeq", mamberSeq);
    	if(!isPhysical) requestMap.put("delYn", "Y");
    	
    	if((!isPhysical ? this.dao.dbUpdate("post.setPost", requestMap) : this.dao.dbDelete("post.removePost", requestMap)) < 0 ) throw new BadRequestException(!isPhysical ? "���� ������ �����߽��ϴ�." : "������ ������ �����߽��ϴ�.");
    	return "�Խñ��� �����Ǿ����ϴ�.";
    }

}
