package site.kongdroid.api.service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface MemberService {
    Map<String, Object> getMembers(Map<String, Object> requestMap, boolean isDouble);
    Map<String, Object> getMember(Integer memberSeq);
    Map<String, Object> handleMember(Integer memberSeq, Map<String, Object> requestMap, boolean isAdd, String whatAct)
            throws InvalidAlgorithmParameterException, UnsupportedEncodingException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
}
