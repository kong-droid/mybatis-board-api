package site.kongdroid.api.service;

import java.util.Map;

public interface PostService {
    Map<String, Object> getPosts(Map<String, Object> requestMap, boolean isDouble);
    Map<String, Object> handlePost(Map<String, Object> requestMap, boolean isAdd, boolean isPhysical, String whatAct);
    void setPostForViewCount(Map<String, Object> requestMap);
}
