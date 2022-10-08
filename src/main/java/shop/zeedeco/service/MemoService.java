package shop.zeedeco.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import shop.zeedeco.dao.CustomDao;
import shop.zeedeco.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class MemoService {
	
	private final CustomDao dao;
	
    public Map<String, Object> getMemos(Map<String, Object> requestMap, boolean isDouble) throws BadRequestException {
        Map<String, Object> responseMap = isDouble ? new HashMap<>() : dao.dbDetail("memo.getMemos", requestMap);  
        if(isDouble) {
            responseMap.put("menos", dao.dbDetails("memo.getMemos", requestMap));
        }
    	return responseMap;
    }

    public Map<String, Object> handleMemo ( Map<String, Object> requestMap, boolean isAdd, String whatAct ) throws InternalResourceException {
    	Map<String, Object> responseMap = new HashMap<String, Object>();
        switch (whatAct) {
            case "regist":
            case "modify":
                if(dao.dbInsert(isAdd ? "memo.addMemo" : "memo.setMemo", requestMap) < 0) new BadRequestException("Invalid Error");
                break;
            case "remove":
                if(dao.dbDelete("memo.removeMemo", requestMap) < 0) new BadRequestException("Invalid Error");
                break;
        }
        responseMap.put("memoSEQ", requestMap.get("memoSEQ"));
        return responseMap;
    }    
}
