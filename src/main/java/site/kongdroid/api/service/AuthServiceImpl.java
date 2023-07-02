package site.kongdroid.api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.val;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.config.jwt.JwtTokenProvider;
import site.kongdroid.api.constants.MessageConstant;
import site.kongdroid.api.constants.UserRole;
import site.kongdroid.api.dao.CustomDao;
import site.kongdroid.api.exception.InternalServerException;
import site.kongdroid.api.exception.ResourceNotFoundException;

import javax.security.auth.message.AuthException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final CustomDao dao;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	public Map<String, Object> getAuth(String userAgent, Map<String, Object> requestMap) throws AuthException {
    	val member = dao.dbDetail("member.getMembers", requestMap);
		val resultMap = new HashMap<String, Object>();
		if(!member.isEmpty()) {
			if(passwordEncoder.matches(String.valueOf(requestMap.get("password")),
					String.valueOf(member.get("password")))) {
				val jwt = jwtTokenProvider.create(userAgent,
						(Integer) member.get("memberSeq"), UserRole.valueOf((String) member.get("role")));
				resultMap.put("jwt", jwt);
			} else {
				throw new AuthException(MessageConstant.NOT_FOUND_MESSAGE);
			}
		} else {
        	throw new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE);
        }
        return resultMap;
    }


    
    public Map<String, Object> getDuplicate(Map<String, Object> requestMap) {
    	val resultMap = new HashMap<String, Object>();
    	val responseMap = Optional.ofNullable(dao.dbDetail("member.getMembers", requestMap));
		if(responseMap.isPresent()) resultMap.put("message", MessageConstant.DUPLICATE_CHECK_TRUE);
        else resultMap.put("message", MessageConstant.DUPLICATE_CHECK_FALSE);
		return resultMap;
    }
    
    public Map<String, Object> setPassword(Integer memberSeq, Map<String, Object> requestMap) {
		val chkMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<>();
		chkMap.put("memberSeq", memberSeq);
		chkMap.put("newPassword", passwordEncoder.encode((String) requestMap.get("newPassword")));
		Map<String, Object> responseMap = dao.dbDetail("member.getMembers", chkMap);
		if (!responseMap.isEmpty()) {
			if (passwordEncoder.matches((String) requestMap.get("oldPassword"), (String) responseMap.get("password"))) {
				if (dao.dbUpdate("member.setMember", chkMap) > 0) {
					resultMap.put("message", MessageConstant.SUCCESS);
					return resultMap;
				} else {
					resultMap.put("message", MessageConstant.FAIL);
					return resultMap;
				}
			} else {
				resultMap.put("message", MessageConstant.FAIL);
				return resultMap;
			}
		} else {
			resultMap.put("message", MessageConstant.FAIL);
			return resultMap;
		}
	}
}
