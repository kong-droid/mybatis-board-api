package site.kongdroid.api.service;

import java.util.Map;

public interface CommentService {
    Map<String, Object> getComments(Map<String, Object> requestMap);
    Map<String, Object> handleComment (Map<String, Object> requestMap, boolean isAdd, String whatAct);
}
