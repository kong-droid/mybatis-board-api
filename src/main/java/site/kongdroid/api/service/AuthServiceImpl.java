package site.kongdroid.api.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;
import lombok.val;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dao.CustomDao;
import site.kongdroid.api.exception.BadRequestException;
import site.kongdroid.api.exception.ResourceNotFoundException;
import site.kongdroid.api.util.MemberEnDecoder;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final CustomDao dao;
	private final MemberEnDecoder memberEnDecoder;
	
    public Map<String, Object> getAuth(Map<String, Object> requestMap) {
    	val responseMap = dao.dbDetail("member.getMembers", requestMap);
        if(!CollectionUtils.isEmpty(responseMap)) {
			val details = dao.dbDetails("member.getMemberDetails", requestMap);
        	memberEnDecoder.decodeMember(responseMap);
        	responseMap.put("details", details);
        } else {
        	new ResourceAccessException("Invalid Error");
        }
             
        return responseMap;
    }
    
    public Map<String, Object> getDuplicate(Map<String, Object> requestMap) {
    	val resultMap = new HashMap<String, Object>();
    	val responseMap = dao.dbDetail("member.getMembers", requestMap);

		if(!CollectionUtils.isEmpty(responseMap)) resultMap.put("message", "already exists");
        else resultMap.put("message", "available");

		return resultMap;
    }
    
    public void setPassword(Map<String, Object> requestMap) {
    	val chkMap = new HashMap<String, Object>();
    	chkMap.put("memberSeq", requestMap.get("memberSeq"));
    	chkMap.put("oldPassword", requestMap.get("oldPassword"));
    	chkMap.put("newPassword", requestMap.get("newPassword"));
    	Map<String, Object> responseMap = dao.dbDetail("member.getMembers", chkMap);
    	if(!CollectionUtils.isEmpty(responseMap)) {
    	    if(dao.dbUpdate("member.setMember", chkMap) < 0) new BadRequestException("Invalid Error");  		
    	} else {
    		new ResourceNotFoundException("Invalid Error");
    	}   
    }
    
}
