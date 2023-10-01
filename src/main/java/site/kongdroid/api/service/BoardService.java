package site.kongdroid.api.service;

import java.util.Map;

public interface BoardService {
    Map<String, Object> getBoards(Map<String, Object> requestMap, boolean isDouble);
    Map<String, Object> handleBoard(Integer memberSeq, Map<String, Object> requestMap,
                                    boolean isAdd, boolean isPhysical, String whatAct);
}
