package shop.zeedeco.service.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.zeedeco.dao.CustomDao;
import shop.zeedeco.exception.BadRequestException;
import shop.zeedeco.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final CustomDao dao;
	
    public Map<String, Object> getComments(Map<String, Object> requestMap, Integer page, Integer size) throws Exception {
        
    	Map<String, Object> responseMap = new HashMap<>();
        
        if(page != null && size != null) {
        	requestMap.put("startRow", page * size);
            requestMap.put("size", size);
        }
        
        List<Map<String, Object>> comments = 	dao.dbDetails("comment.getComments", requestMap);
        Map<String, Object> listCount = 		dao.dbDetail("comment.getCommentsCnt", requestMap);
        Integer totalCount = Integer.parseInt(String.valueOf(listCount.get("cnt")));
        if(comments != null) {
            responseMap.put("comments", comments);
            responseMap.put("totalCount", totalCount);
        } else {
        	new ResourceNotFoundException("데이터가 없습니다.");
        }
        return responseMap;
    }

    public void addComment(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbInsert("comment.addComment", requestMap);
    	if(effectRow < 0) throw new BadRequestException("댓글 등록을 실패했습니다.");
    }
    
    public void setComment(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbUpdate("comment.setComment", requestMap);
    	if(effectRow < 0) throw new BadRequestException("댓글 수정을 실패했습니다.");
    }
    
    public void physicalRemoveComment(int commentSeq) throws Exception {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("commentSeq", commentSeq);
    	int effectRow = this.dao.dbDelete("comment.removeComment", requestMap);
    	if(effectRow < 0) throw new BadRequestException("물리적 삭제를 실패했습니다.");
    }
}
