package shop.zeedeco.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import lombok.RequiredArgsConstructor;
import shop.zeedeco.dao.CustomDao;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final CustomDao dao;
	
    public Map<String, Object> getMembers(Map<String, Object> requestMap, Integer page, Integer size) throws Exception {
        
    	Map<String, Object> responseMap = new HashMap<>();
        
        if(page != null && size != null) {
        	requestMap.put("startRow", page * size);
            requestMap.put("size", size);
        }
        
        List<Map<String, Object>> list = dao.dbDetails("member.getMembers", requestMap);
        int totalCount = dao.dbInsert("member.getMembersCnt", requestMap);
        
        if(list != null) {
            responseMap.put("list", list);
            responseMap.put("totalCount", totalCount);
        } else {
        	new ResourceAccessException("데이터가 없습니다.");
        }
        return responseMap;
    }

    public Map<String, Object> getMember(int memberSeq) throws Exception {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("memberSeq", memberSeq);
    	Map<String, Object> responseMap = dao.dbDetail("member.getMembers", requestMap);
    	List<Map<String, Object>> details = null;
    	
        if(responseMap != null) {
        	details = dao.dbDetails("member.getMemberDetails", requestMap);
        	responseMap.put("details", details);
        } else {
        	new ResourceAccessException("데이터가 없습니다.");
        }
             
        return responseMap;
    }

    public void addMember(Map<String, Object> requestMap) throws Exception {
    	this.dao.dbInsert("member.addMember", requestMap);
    }
    
    public void setMember(Map<String, Object> requestMap) throws Exception {
    	this.dao.dbUpdate("member.setMember", requestMap);
    }
    
    public void logicalRemoveMember(Map<String, Object> requestMap) throws Exception {
    	this.dao.dbUpdate("member.setMember", requestMap);
    }
    
    public void physicalRemoveMember(int memberSeq) throws Exception {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("memberSeq", memberSeq);
    	this.dao.dbDelete("member.removeMember", requestMap);
    }
}
