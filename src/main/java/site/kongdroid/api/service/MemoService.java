package site.kongdroid.api.service;

import java.util.Map;

public interface MemoService {
    Map<String, Object> getMemos(Integer memberSeq, Map<String, Object> requestMap);
    Map<String, Object> handleMemo (Integer memberSeq, Map<String, Object> requestMap, boolean isAdd, String whatAct);
}
