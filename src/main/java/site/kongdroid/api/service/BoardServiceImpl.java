package site.kongdroid.api.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.val;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dao.CustomDao;
import site.kongdroid.api.exception.BadRequestException;
import site.kongdroid.api.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final CustomDao dao;
	private final MemberServiceImpl memberService;
	
    public Map<String, Object> getBoards(Map<String, Object> requestMap, boolean isDouble) throws InternalResourceException {
    	Map<String, Object> responseMap = new HashMap<>();
    	if(isDouble) {
    	    val searchMap = (Map<String, Object>) requestMap.get("search");
    	    if(!CollectionUtils.isEmpty(searchMap)) {
    	        if(searchMap.get("page") != null && searchMap.get("size") != null) {
    	            searchMap.put("startRow", (Integer) searchMap.get("page") * (Integer) searchMap.get("page"));
    	        }
    	    }   
    	    
    	    val boards = dao.dbDetails("board.getBoards", requestMap);
    	    if(!CollectionUtils.isEmpty(boards)) {
    	        boards.forEach(board -> {
    	            val reqMap = new HashMap<String, Object>();
    	            reqMap.put("memberSeq", board.get("createdNo"));
    	            board.put("memberInfo", memberService.getMembers(reqMap, false));
    	        });
    	    }
    	    
            responseMap.put("boards", boards);
            responseMap.put("totalCount", Integer.parseInt(String.valueOf(dao.dbDetail("board.getBoardsCnt", requestMap).get("cnt"))));
    	} else {
    	    responseMap = dao.dbDetail("board.getBoards", requestMap);
            if(!CollectionUtils.isEmpty(responseMap)) {
                val reqMap = new HashMap<String, Object>();
                reqMap.put("memberSeq", responseMap.get("createdNo"));
                responseMap.put("memberInfo", memberService.getMembers(reqMap, false));
            }
    	}
        return responseMap;
    }

    public Map<String, Object> handleBoard(Map<String, Object> requestMap, boolean isAdd, boolean isPhysical, String whatAct) throws InternalResourceException {
        val responseMap = new HashMap<String, Object>();
        switch (whatAct) {
            case "regist":
            case "modify":
                if(dao.dbInsert(isAdd ? "board.addBoard" : "board.setBoard" , requestMap) < 0) throw new BadRequestException("Invalid Error");
                break;
            case "remove":
                val rmvReqMap = new HashMap<String, Object>();
                val handle = ( Map<String, Object> ) requestMap.get("handle");
                rmvReqMap.put("boardSeq", requestMap.get("boardSeq"));
                val rmvResMap = getBoards(rmvReqMap, false);
                if(!CollectionUtils.isEmpty(rmvResMap)) {
                    if(rmvResMap.get("createdNo").equals(handle.get("memberSeq"))) {
                        if(isPhysical) {
                            if(dao.dbDelete("board.removeBoard", requestMap) < 0 ) throw new BadRequestException("Invalid Error");
                        } else {
                            handle.put("delYn", "Y");
                            if(dao.dbUpdate("board.setBoard", requestMap) < 0) throw new BadRequestException("Invalid Error");
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
