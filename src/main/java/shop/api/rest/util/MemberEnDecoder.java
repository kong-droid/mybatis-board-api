package shop.api.rest.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author KMH
 * 22.02.02
 * 암호화 / 복호화 클래스 
 **/
@Component
public class MemberEnDecoder {

	@Autowired
	Aes256Util aes256Util;
    public Map<String, Object> encodeMember(Map<String, Object> requestMap) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException  {
    	if(requestMap.get("email") != null) requestMap.put("email", aes256Util.aesEncode((String) requestMap.get("email")));
    	if(requestMap.get("phone") != null)requestMap.put("phone", aes256Util.aesEncode((String) requestMap.get("phone")));
    	if(requestMap.get("addr") != null && !requestMap.get("addr").equals("")) requestMap.put("addr", aes256Util.aesEncode((String) requestMap.get("addr")));
        return requestMap;
    }
    
    /** 복호화 **/
    public Map<String, Object> decodeMember(Map<String, Object> responseMap) {
        if(responseMap.get("email") != null) responseMap.put("email", aes256Util.aesDecode((String) responseMap.get("email")));
        if(responseMap.get("phone") != null) responseMap.put("phone", aes256Util.aesDecode((String) responseMap.get("phone")));
    	if(responseMap.get("addr") != null && !responseMap.get("addr").equals("")) responseMap.put("addr", aes256Util.aesDecode((String) responseMap.get("addr")));
        return responseMap;
    }

    public List<Map<String, Object>> decodeMembers(List<Map<String, Object>> responseMaps) {
        for(Map<String, Object> map : responseMaps) {
            if(map.get("email") != null) map.put("email", aes256Util.aesDecode((String) map.get("email")));
            if(map.get("phone") != null) map.put("phone", aes256Util.aesDecode((String) map.get("phone")));
        	if(map.get("addr") != null && !map.get("addr").equals("")) map.put("addr", aes256Util.aesDecode((String) map.get("addr")));
        }
        return responseMaps;
    }
}
