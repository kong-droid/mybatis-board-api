package shop.zeedeco.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapUtil {
	
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(final Object obj) {
        if(obj == null) return new HashMap<>();
        return new ObjectMapper().convertValue(obj, Map.class);
    }

    public static List<Map<String, Object>> toListMap(final List<? extends Object> objList) {
        return objList.stream().map(MapUtil::toMap).collect(Collectors.toList());
    }

    public static boolean isEmpty(final Map<?, ?> m) {
        return m == null || m.isEmpty();
    }
    
}
