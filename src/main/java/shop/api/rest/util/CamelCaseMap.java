package shop.api.rest.util;

import java.util.HashMap;

import org.springframework.jdbc.support.JdbcUtils;

/**
 * @author KMH 
 * 2021.12.27
 * sql�� �ʵ尪 snake������ camel�� ������
 **/
public class CamelCaseMap extends HashMap<String, Object> {
	
	@Override
	public Object put(String key, Object value) {
		return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
	}
	
}

