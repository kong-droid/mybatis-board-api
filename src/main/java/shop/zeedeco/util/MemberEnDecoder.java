package shop.zeedeco.util;

import java.util.List;
import java.util.Map;

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
	
    /** 암호화 **/
    public Map<String, Object> encodeMember(Map<String, Object> requestMap) throws Exception {
    	requestMap.put("email", 	aes256Util.aesEncode((String) requestMap.get("email")));
    	requestMap.put("phone", 	aes256Util.aesEncode((String) requestMap.get("phone")));
    	if(requestMap.get("addr") != null && !requestMap.get("addr").equals("")) requestMap.put("addr", 		aes256Util.aesEncode((String) requestMap.get("addr")));
        return requestMap;
    }
    
    /** 복호화 **/
    public Map<String, Object> decodeMember(Map<String, Object> responseMap) throws Exception {
    	responseMap.put("email", 	aes256Util.aesDecode((String) responseMap.get("email")));
    	responseMap.put("phone", 	aes256Util.aesDecode((String) responseMap.get("phone")));
    	if(responseMap.get("addr") != null && !responseMap.get("addr").equals("")) responseMap.put("addr", 		aes256Util.aesDecode((String) responseMap.get("addr")));
        return responseMap;
    }

    public List<Map<String, Object>> decodeMembers(List<Map<String, Object>> responseMaps) throws Exception {
        for(Map<String, Object> map : responseMaps) {
        	map.put("email", 		aes256Util.aesDecode((String) map.get("email")));
        	map.put("phone",	 	aes256Util.aesDecode((String) map.get("phone")));
        	if(map.get("addr") != null && !map.get("addr").equals("")) map.put("addr", 		aes256Util.aesDecode((String) map.get("addr")));
        }
        return responseMaps;
    }
}
