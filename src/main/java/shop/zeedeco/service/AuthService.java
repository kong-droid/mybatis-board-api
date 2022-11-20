package shop.zeedeco.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;
import lombok.RequiredArgsConstructor;
import shop.zeedeco.dao.CustomDao;
import shop.zeedeco.exception.BadRequestException;
import shop.zeedeco.exception.ResourceNotFoundException;
import shop.zeedeco.util.MemberEnDecoder;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final CustomDao dao;
	private final MemberEnDecoder memberEnDecoder;
	
    public Map<String, Object> getAuth(Map<String, Object> requestMap) {
    	Map<String, Object> responseMap = dao.dbDetail("member.getMembers", requestMap);
    	List<Map<String, Object>> details = null;
    	
        if(!CollectionUtils.isEmpty(responseMap)) {
        	details = dao.dbDetails("member.getMemberDetails", requestMap);
        	this.memberEnDecoder.decodeMember(responseMap);
        	responseMap.put("details", details);
        } else {
        	new ResourceAccessException("Invalid Error");
        }
             
        return responseMap;
    }
    
    public Map<String, Object> getDuplicate(Map<String, Object> requestMap) {
    	Map<String, Object> resultMap = new HashMap<>();
    	Map<String, Object> responseMap = dao.dbDetail("member.getMembers", requestMap);
        if(!CollectionUtils.isEmpty(responseMap)) {
        	resultMap.put("message", "already exists");
        } else {
        	resultMap.put("message", "available");
        }
             
        return resultMap;
    }
    
    public void setPassword(Map<String, Object> requestMap) {
    	Map<String, Object> chkMap = new HashMap<>();
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
