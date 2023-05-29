package site.kongdroid.api.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dao.CustomDao;
import site.kongdroid.api.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {
	
	private final CustomDao dao;
	
    public Map<String, Object> getMemos(Map<String, Object> requestMap, boolean isDouble) throws InternalResourceException {
        Map<String, Object> responseMap = isDouble ? new HashMap<>() : dao.dbDetail("memo.getMemos", requestMap);  
        if(isDouble) {
            responseMap.put("memos", dao.dbDetails("memo.getMemos", requestMap));
        }
    	return responseMap;
    }

    public Map<String, Object> handleMemo (Map<String, Object> requestMap, boolean isAdd, String whatAct) throws InternalResourceException {
    	Map<String, Object> responseMap = new HashMap<>();
        switch (whatAct) {
            case "regist":
            case "modify":
                if(dao.dbInsert(isAdd ? "memo.addMemo" : "memo.setMemo", requestMap) < 0) new BadRequestException("Invalid Error");
                break;
            case "remove":
                if(dao.dbDelete("memo.removeMemo", requestMap) < 0) new BadRequestException("Invalid Error");
                break;
        }
        responseMap.put("memoSeq", requestMap.get("memoSeq"));
        return responseMap;
    }    
}
