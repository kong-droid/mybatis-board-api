package site.kongdroid.api.service;

import java.util.Map;

public interface AuthService {
    Map<String, Object> getAuth(Map<String, Object> requestMap);
    Map<String, Object> getDuplicate(Map<String, Object> requestMap);
    void setPassword(Map<String, Object> requestMap);
}
