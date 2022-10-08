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
	
    public Map<String, Object> getBoards( Map<String, Object> requestMap, boolean isDouble ) throws InternalResourceException {
    	Map<String, Object> responseMap = !isDouble ? dao.dbDetail("board.getBoards", requestMap) : new HashMap<>();
    	if(isDouble) {
    	    Map<String, Object> searchMap = (Map<String, Object>) requestMap.get("search");    	    
    	    if(!CollectionUtils.isEmpty(searchMap)) {
    	        if(searchMap.get("page") != null && searchMap.get("size") != null) {
    	            searchMap.put("startRow", (Integer) searchMap.get("page") * (Integer) searchMap.get("page"));
    	        }
    	    }   
            responseMap.put("boards", dao.dbDetails("board.getBoards", requestMap));
            responseMap.put("totalCount", Integer.parseInt(String.valueOf(dao.dbDetail("board.getBoardsCnt", requestMap).get("cnt"))));
    	}
        return responseMap;
    }

    public Map<String, Object> handleBoard ( Map<String, Object> requestMap, boolean isAdd, boolean isPhysical, String whatAct ) throws InternalResourceException {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        switch (whatAct) {
            case "regist":
            case "modify":
                if(dao.dbInsert(isAdd ? "board.addBoard" : "board.setBoard" , requestMap) < 0) throw new BadRequestException("Invalid Error");
                break;
            case "remove":
                Map<String, Object> rmvReqMap = new HashMap<>();
                Map<String, Object> handle = ( Map<String, Object> ) requestMap.get("handle");
                rmvReqMap.put("boardSeq", requestMap.get("boardSeq"));
                Map<String, Object> rmvResMap = this.getBoards(rmvReqMap, false);
                if(!CollectionUtils.isEmpty(rmvResMap)) {
                    if(rmvResMap.get("createdNo").equals(handle.get("memberSeq"))) {
                        if(isPhysical) {
                            if(this.dao.dbDelete("board.removeBoard", requestMap) < 0 ) throw new BadRequestException("Invalid Error");
                        } else {
                            handle.put("delYn", "Y");
                            if(this.dao.dbUpdate("board.setBoard", requestMap) < 0) throw new BadRequestException("Invalid Error");
                        }
                    } else {
                        throw new BadRequestException("Invalid Error");
                    }
                } else {
                    new ResourceNotFoundException("Invalid Error");
                }
                break;
        }
        responseMap.put("boardSeq", requestMap.get("boardSeq"));
        return responseMap;
    }

}
