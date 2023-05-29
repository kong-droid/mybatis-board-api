package site.kongdroid.api.service;

import java.util.Map;

public interface MemoService {
    Map<String, Object> getMemos(Map<String, Object> requestMap, boolean isDouble);
    Map<String, Object> handleMemo (Map<String, Object> requestMap, boolean isAdd, String whatAct);
}
