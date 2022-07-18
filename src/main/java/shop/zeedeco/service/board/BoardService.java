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
public class BoardService {
	
	private final CustomDao dao;
	
    public Map<String, Object> getBoards(Map<String, Object> requestMap, Integer page, Integer size) throws Exception {
        
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

    public Map<String, Object> getBoard(int boardSeq) throws Exception {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("boardSeq", boardSeq);
    	Map<String, Object> responseMap = dao.dbDetail("board.getBoards", requestMap);
    	if(responseMap == null) new ResourceNotFoundException("게시판 상세정보를 찾을 수 없습니다.");
    	return responseMap;
    }

    public void addBoard(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbInsert("board.addBoard", requestMap);
    	if(effectRow < 0) throw new BadRequestException("게시판 등록을 실패했습니다.");
    }
    
    public void setBoard(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbUpdate("board.setBoard", requestMap);
    	if(effectRow < 0) throw new BadRequestException("게시판 수정을 실패했습니다.");
    }
    
    public void logicalRemoveBoard(Map<String, Object> requestMap) throws Exception {
    	if(this.dao.dbUpdate("board.setBoard", requestMap) < 0) throw new BadRequestException("논리적 삭제를 실패했습니다.");
    }
    
    public void physicalRemoveBoard(int boardSeq) throws Exception {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("boardSeq", boardSeq);
    	int effectRow = this.dao.dbDelete("board.removeBoard", requestMap);
    	if(effectRow < 0) throw new BadRequestException("물리적 삭제를 실패했습니다.");
    }
}
