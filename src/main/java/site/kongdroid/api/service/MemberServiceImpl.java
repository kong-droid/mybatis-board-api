package site.kongdroid.api.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

import lombok.val;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import site.kongdroid.api.constants.MessageConstant;
import site.kongdroid.api.dao.CustomDao;
import site.kongdroid.api.exception.BadRequestException;
import site.kongdroid.api.exception.InternalServerException;
import site.kongdroid.api.util.MemberEnDecoder;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final CustomDao dao;
	private final MemberEnDecoder memberEnDecoder;
	
    public Map<String, Object> getMembers(Map<String, Object> requestMap, boolean isDouble) throws BadRequestException {
        Map<String, Object> responseMap = isDouble
                ? new HashMap<>()
                : memberEnDecoder.decodeMember(dao.dbDetail("member.getMembers", requestMap));

        if(isDouble) {
            val members = memberEnDecoder.decodeMembers(dao.dbDetails("member.getMembers", requestMap));
            responseMap.put("totalCount", dao.dbCount("member.getMembersCnt", requestMap));
            responseMap.put("members", members);
        } else {
            responseMap.put("details", dao.dbDetails("member.getMemberDetails", requestMap));
        }
        return responseMap;
    }

    @Transactional
    public Map<String, Object> handleMember(Integer memberSeq, Map<String, Object> requestMap,
                                            boolean isAdd, String whatAct)
            throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(!isAdd) requestMap.put("memberSeq", memberSeq);
        val responseMap = new HashMap<String, Object>();
        switch (whatAct) {
            case "regist":
            case "modify":
                if(dao.dbInsert(isAdd
                        ? "member.addMember"
                        : "member.setMember", memberEnDecoder.encodeMember(requestMap)) < 0)
                    throw new InternalServerException(MessageConstant.INVALID_MESSAGE);

                if(!isAdd)
                    if(dao.dbDelete("member.removeMemberDetail", requestMap) < 0)
                        throw new InternalServerException(MessageConstant.INVALID_MESSAGE);

                val details = (List<Map<String, Object>>) requestMap.get("details");
                if(!details.isEmpty()) {
                    details.forEach(detail -> {
                        detail.put("memberSeq", requestMap.get("memberSeq"));
                        if(dao.dbInsert("member.addMemberDetail", detail) < 0)
                            throw new InternalServerException(MessageConstant.INVALID_MESSAGE);
                    });
                }
                break;
            case "remove":
                System.err.println("test ::::" + requestMap);
                if(dao.dbDelete("member.removeMemberDetail", requestMap) < 0)
                    throw new InternalServerException(MessageConstant.INVALID_MESSAGE);
                if(dao.dbDelete("member.removeMember", requestMap) < 0)
                    throw new InternalServerException(MessageConstant.INVALID_MESSAGE);
                break;
        }
        responseMap.put("memberSeq", !isAdd ? requestMap.get("memberSeq") : memberSeq);
        return responseMap;
    } 
}
