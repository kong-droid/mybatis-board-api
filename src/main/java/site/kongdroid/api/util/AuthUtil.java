package site.kongdroid.api.util;

import org.springframework.security.core.Authentication;
import site.kongdroid.api.constants.MessageConstant;
import javax.security.auth.message.AuthException;

public class AuthUtil {
    public static Integer memberSeq(Authentication authentication) throws AuthException {
        Integer seq =  Integer.parseInt(authentication.getName());
        if(seq != null) return seq;
        else throw new AuthException(MessageConstant.NOT_FOUND_MESSAGE);
    }
}
