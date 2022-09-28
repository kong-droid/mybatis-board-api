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
        	new ResourceNotFoundException("�����Ͱ� �����ϴ�.");
        }
        return responseMap;
    }

    public Map<String, Object> getBoard(int boardSeq) throws InternalResourceException {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("boardSeq", boardSeq);
    	Map<String, Object> responseMap = dao.dbDetail("board.getBoards", requestMap);
    	if(CollectionUtils.isEmpty(responseMap)) new ResourceNotFoundException("�Խ��� �������� ã�� �� �����ϴ�.");
    	return responseMap;
    }

    public Integer handleBoard ( Map<String, Object> requestMap, Integer boardSeq ) throws InternalResourceException {
    	if(boardSeq == null) {
    		if (this.dao.dbInsert("board.addBoard", requestMap) < 0) throw new BadRequestException("�Խ��� ��Ͽ� �����߽��ϴ�.");
    		boardSeq = Integer.parseInt(String.valueOf(requestMap.get("boardSeq")));
    	} else {
    		if (this.dao.dbUpdate("board.setBoard", requestMap) < 0) throw new BadRequestException("�Խ��� ������ �����߽��ϴ�.");
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
    	    		if(this.dao.dbDelete("board.removeBoard", requestMap) < 0 ) throw new BadRequestException("������ ������ �����߽��ϴ�.");
    	    	} else {
    	    		requestMap.put("delYn", "Y");
    	    		requestMap.put("memberSeq", memberSeq);
    	    		if(this.dao.dbUpdate("board.setBoard", requestMap) < 0) throw new BadRequestException("���� ������ �����߽��ϴ�.");
    	    	}
    		} else {
    			throw new BadRequestException("�Խñ� ����ڰ� �ƴմϴ�.");
    		}
    	} else {
    		new ResourceNotFoundException("������ �Խñ� ������ �����ϴ�.");
    	}
    }
    
}
