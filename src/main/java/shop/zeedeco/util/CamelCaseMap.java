package shop.zeedeco.util;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

/**
 * @author KMH 
 * 2021.12.27
 * sql의 필드값 snake형식을 camel로 변경함
 **/
public class CamelCaseMap extends HashMap<String, Object> {
	
	@Override
	public Object put(String key, Object value) {
		return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
	}
	
}

