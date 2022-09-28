package shop.zeedeco.service;

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
public class MemoService {
	
	private final CustomDao dao;
	
    public Map<String, Object> getMemos(Map<String, Object> requestMap) throws InternalResourceException {
    	Map<String, Object> responseMap = new HashMap<>();  
    	List<Map<String, Object>> memos = dao.dbDetails("memo.getMemos", requestMap);
    	if(CollectionUtils.isEmpty(memos)) new ResourceNotFoundException("�޸� ã�� �� �����ϴ�."); 
    	responseMap.put("memos", memos);
        return responseMap;
    }
    
    public Map<String, Object> getMemo( Integer memoSeq ) throws InternalResourceException {
    	Map<String, Object> requestMap = new HashMap<>();  
    	requestMap.put("memoSeq", memoSeq);
    	Map<String, Object> responseMap = dao.dbDetail("memo.getMemos", requestMap); 
    	if(CollectionUtils.isEmpty(responseMap)) new ResourceNotFoundException("�޸� ã�� �� �����ϴ�."); 
        return responseMap;
    }
    
    public Integer handleMemo ( Map<String, Object> requestMap, Integer memoSeq ) throws InternalResourceException {
    	if(memoSeq == null) {
    		if(dao.dbInsert("memo.addMemo", requestMap) < 0) new BadRequestException("���忡 �����߽��ϴ�.");
    		memoSeq = Integer.parseInt(String.valueOf(requestMap.get("memoSeq")));
 		} else {
 			requestMap.put("memoSeq", memoSeq);
 			if( dao.dbUpdate("memo.setMemo", requestMap) < 0 ) new BadRequestException("������ �����߽��ϴ�.");	
 		}
    	return memoSeq;
    }
    
 	public void physicalRemoveMemo(int memoSeq) throws InternalResourceException {
 		Map<String, Object> requestMap = new HashMap<>();
 		requestMap.put("memoSeq", memoSeq);
 		int effectRow = dao.dbDelete("memo.removeMemo", requestMap);
 		if( effectRow < 0 ) new BadRequestException("������ �����߽��ϴ�.");
 	}
}
