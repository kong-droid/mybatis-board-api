package shop.zeedeco.util;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MapUtil {
	
    public Map<String, Object> toMap() throws Exception {
        return new ObjectMapper().convertValue(this, Map.class);
    }
    
}
