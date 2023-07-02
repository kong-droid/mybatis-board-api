package site.kongdroid.api.service;

import javax.security.auth.message.AuthException;
import java.util.Map;

public interface AuthService {
    Map<String, Object> getAuth(String userAgent, Map<String, Object> requestMap) throws AuthException;
    Map<String, Object> getDuplicate(Map<String, Object> requestMap);
    void setPassword(Map<String, Object> requestMap);
}
