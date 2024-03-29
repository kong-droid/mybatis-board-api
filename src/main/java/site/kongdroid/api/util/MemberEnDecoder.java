package site.kongdroid.api.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author KMH
 * 22.02.02
 * 암호화 / 복호화 클래스 
 **/
@Component
@RequiredArgsConstructor
public class MemberEnDecoder {

    private final AES256 aes256;
    private final PasswordEncoder passwordEncoder;
    public Map<String, Object> encodeMember(Map<String, Object> requestMap) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException  {
        requestMap.put("password", passwordEncoder.encode(String.valueOf(requestMap.get("password"))));
        if(requestMap.get("email") != null) requestMap.put("email", aes256.aesEncode((String) requestMap.get("email")));
    	if(requestMap.get("phone") != null)requestMap.put("phone", aes256.aesEncode((String) requestMap.get("phone")));
        if(requestMap.get("addr") != null && !requestMap.get("addr").equals(""))
            requestMap.put("addr", aes256.aesEncode((String) requestMap.get("addr")));
        return requestMap;
    }
    
    /** 복호화 **/
    public Map<String, Object> decodeMember(Map<String, Object> responseMap) {
        if(!CollectionUtils.isEmpty(responseMap)) {
            if(responseMap.get("email") != null) responseMap.put("email", aes256.aesDecode((String) responseMap.get("email")));
            if(responseMap.get("phone") != null) responseMap.put("phone", aes256.aesDecode((String) responseMap.get("phone")));
            if(responseMap.get("addr") != null && !responseMap.get("addr").equals(""))
                responseMap.put("addr", aes256.aesDecode((String) responseMap.get("addr")));
        }
        return responseMap;
    }

    public List<Map<String, Object>> decodeMembers(List<Map<String, Object>> responseMaps) {
        for(Map<String, Object> map : responseMaps) {
            if(map.get("email") != null) map.put("email", aes256.aesDecode((String) map.get("email")));
            if(map.get("phone") != null) map.put("phone", aes256.aesDecode((String) map.get("phone")));
        	if(map.get("addr") != null && !map.get("addr").equals(""))
                map.put("addr", aes256.aesDecode((String) map.get("addr")));
        }
        return responseMaps;
    }
}
