package shop.api.rest.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;

import lombok.RequiredArgsConstructor;
import shop.api.rest.dao.CustomDao;
import shop.api.rest.exception.BadRequestException;
import shop.api.rest.util.MemberEnDecoder;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final CustomDao dao;
	private final MemberEnDecoder memberEnDecoder;
	
    public Map<String, Object> getMembers(Map<String, Object> requestMap, boolean isDouble) throws BadRequestException {
        Map<String, Object> responseMap = isDouble ? new HashMap<>() : memberEnDecoder.decodeMember(dao.dbDetail("member.getMembers", requestMap));  
        if(isDouble) {
            List<Map<String, Object>> members = memberEnDecoder.decodeMembers(dao.dbDetails("member.getMembers", requestMap));
            if(!CollectionUtils.isEmpty(members)) {
                members.forEach(member -> {
                    Map<String, Object> detReqMap = new HashMap<>();
                    detReqMap.put("memberSeq", member.get("memberSeq"));
                    member.put("details", dao.dbDetails("member.getMemberDetails", requestMap));
                });
            }
            responseMap.put("totalCount", dao.dbDetail("member.getMembersCnt", requestMap).get("cnt"));
            responseMap.put("members", members);
        }
        return responseMap;
    }

    public Map<String, Object> handleMember ( Map<String, Object> requestMap, boolean isAdd, String whatAct ) throws InternalResourceException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        switch (whatAct) {
            case "regist":
            case "modify":
                if(dao.dbInsert(isAdd ? "member.addMember" : "member.setMember", memberEnDecoder.encodeMember(requestMap)) < 0) new BadRequestException("Invalid Error");
                if(!isAdd) if(dao.dbDelete("member.removeMemberDetail", requestMap) < 0) new BadRequestException("Invalid Error");
                List<Map<String, Object>> details = (List<Map<String, Object>>) requestMap.get("details");
                if(!CollectionUtils.isEmpty(details)) {
                    details.forEach(detail -> {
                        if(dao.dbInsert("member.addMemberDetail", requestMap) < 0) new BadRequestException("Invalid Error");
                    });
                }
                break;
            case "remove":
                
                if(dao.dbDelete("member.removeMember", requestMap) < 0) new BadRequestException("Invalid Error");
                break;
        }
        responseMap.put("memberSeq", requestMap.get("memberSeq"));
        return responseMap;
    } 
}
