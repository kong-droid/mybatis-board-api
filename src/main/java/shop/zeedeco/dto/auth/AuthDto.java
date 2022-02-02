package shop.zeedeco.dto.auth;

import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
public class AuthDto {
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewAuthReq {
		@NotNull(message = "아아디 값이 없습니다.")
		private String id;
		@NotEmpty(message = "패스워드 값이 없습니다. ")
		private String password;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SetAuthReq {
		@NotNull(message = "유저고유번호 값이 없습니다.")
		private Integer memberSeq;
		@NotEmpty(message = "패스워드 값이 없습니다. ")
		private String password;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewAuthRes {
		private String duplicateCheck;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
        
        public ViewAuthRes(Map<String, Object> responseMap) {
        	this.duplicateCheck = (String) responseMap.get("duplicateCheck");
        }
	}
}
