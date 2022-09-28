package shop.zeedeco.exception;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

public class CommonExceptionHandler {

	public static Map<String, Object> commonException( HttpServletRequest request ) {
		Integer status = Integer.valueOf((String) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		
		
		return null; 
		
	}
}
