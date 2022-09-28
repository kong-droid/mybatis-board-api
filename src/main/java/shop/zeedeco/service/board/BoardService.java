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
public class BoardService {
	
	private final CustomDao dao;
	
    public Map<String, Object> getBoards( Map<String, Object> requestMap, Integer page, Integer size ) throws InternalResourceException {
        
    	Map<String, Object> responseMap = new HashMap<>();
        
        if(page != null && size != null) {
        	requestMap.put("startRow", page * size);
            requestMap.put("size", size);
        }
        
        List<Map<String, Object>> boards = dao.dbDetails("board.getBoards", requestMap);
        Map<String, Object> listCount = dao.dbDetail("board.getBoardsCnt", requestMap);
        Integer totalCount = Integer.parseInt(String.valueOf(listCount.get("cnt")));
        if(!CollectionUtils.isEmpty(boards)) {
            responseMap.put("boards", boards);
            responseMap.put("totalCount", totalCount);
        } else {
        	new ResourceNotFoundException("데이터가 없습니다.");
        }
        return responseMap;
    }

    public Map<String, Object> getBoard(int boardSeq) throws InternalResourceException {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("boardSeq", boardSeq);
    	Map<String, Object> responseMap = dao.dbDetail("board.getBoards", requestMap);
    	if(CollectionUtils.isEmpty(responseMap)) new ResourceNotFoundException("게시판 상세정보를 찾을 수 없습니다.");
    	return responseMap;
    }

    public Integer handleBoard ( Map<String, Object> requestMap, Integer boardSeq ) throws InternalResourceException {
    	if(boardSeq == null) {
    		if (this.dao.dbInsert("board.addBoard", requestMap) < 0) throw new BadRequestException("게시판 등록에 실패했습니다.");
    		boardSeq = Integer.parseInt(String.valueOf(requestMap.get("boardSeq")));
    	} else {
    		if (this.dao.dbUpdate("board.setBoard", requestMap) < 0) throw new BadRequestException("게시판 수정에 실패했습니다.");
    	}
    	return boardSeq;
    }
    
    public void handleRemoveBoard ( Integer boardSeq, Integer memberSeq, boolean isPhysical ) throws InternalResourceException {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("boardSeq", boardSeq);
    	Map<String, Object> responseMap = dao.dbDetail("board.getBoards", requestMap);
    	if(!CollectionUtils.isEmpty(responseMap)) {
    		if(responseMap.get("createdNo") == memberSeq) {
    	    	if(isPhysical) {
    	    		if(this.dao.dbDelete("board.removeBoard", requestMap) < 0 ) throw new BadRequestException("물리적 삭제를 실패했습니다.");
    	    	} else {
    	    		requestMap.put("delYn", "Y");
    	    		requestMap.put("memberSeq", memberSeq);
    	    		if(this.dao.dbUpdate("board.setBoard", requestMap) < 0) throw new BadRequestException("논리적 삭제를 실패했습니다.");
    	    	}
    		} else {
    			throw new BadRequestException("게시글 등록자가 아닙니다.");
    		}
    	} else {
    		new ResourceNotFoundException("삭제할 게시글 정보가 없습니다.");
    	}
    }
    
}
