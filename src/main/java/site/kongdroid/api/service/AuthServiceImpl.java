package site.kongdroid.api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;
import lombok.val;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.config.jwt.JwtTokenProvider;
import site.kongdroid.api.constants.UserRole;
import site.kongdroid.api.dao.CustomDao;
import site.kongdroid.api.exception.BadRequestException;
import site.kongdroid.api.exception.ResourceNotFoundException;
import site.kongdroid.api.util.MemberEnDecoder;

import javax.security.auth.message.AuthException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final CustomDao dao;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberEnDecoder memberEnDecoder;
	private final PasswordEncoder passwordEncoder;

	public Map<String, Object> getAuth(Map<String, Object> requestMap) throws AuthException {
    	requestMap.put("password", passwordEncoder.encode(String.valueOf(requestMap.get("password"))));
		val responseMap = dao.dbDetail("member.getMembers", requestMap);
        if(!CollectionUtils.isEmpty(responseMap)) {
			jwtTokenProvider.create(null, (Integer) responseMap.get("userNo"), (UserRole) responseMap.get("role"));
			val details = dao.dbDetails("member.getMemberDetails", requestMap);
        	memberEnDecoder.decodeMember(responseMap);
        	responseMap.put("details", details);
        } else {
        	throw new AuthException("Invalid Error");
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
