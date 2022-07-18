package shop.zeedeco.dto.member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
public class MemberDto {
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewMemberRes {
		private Integer memberSeq;
		private String id;
		private String name;
		private String role;
		private String profile;
		private String email;
		private String phone;
		private String addr;
		private String remark;
		private String socialYn;
		private String delYn;
		private LocalDateTime createdDt;
		private LocalDateTime updatedDt;
		private Integer updatedNo;
		private List<MemberDetailDto.ViewMemberDetailRes> details;
		
		public ViewMemberRes(Map<String, Object> responseMap) {
			this.memberSeq			= (Integer) 	responseMap.get("memberSeq");
			this.id					= (String) 		responseMap.get("id");
			this.name				= (String) 		responseMap.get("name");
			this.role				= (String) 		responseMap.get("role");
			this.profile			= (String) 		responseMap.get("profile")			== null ? null : (String) 	responseMap.get("profile");
			this.email				= (String) 		responseMap.get("email")			== null ? null : (String) 	responseMap.get("email");
			this.phone				= (String) 		responseMap.get("phone")			== null ? null : (String) 	responseMap.get("phone");
			this.addr				= (String) 		responseMap.get("addr")				== null ? null : (String) 	responseMap.get("addr");
			this.remark				= (String) 		responseMap.get("remark")			== null ? null : (String) 	responseMap.get("remark");
			this.socialYn			= (String) 		responseMap.get("socialYn")			== null ? null : (String) 	responseMap.get("socialYn");
			this.delYn				= (String) 		responseMap.get("delYn")			== null ? null : (String) 	responseMap.get("delYn");
			this.createdDt			= (LocalDateTime) 	responseMap.get("createdDt") 	== null ? null : (LocalDateTime) 	responseMap.get("createdDt");
			this.updatedDt			= (LocalDateTime) 	responseMap.get("updatedDt") 	== null ? null : (LocalDateTime) 	responseMap.get("updatedDt");
			this.updatedNo			= (Integer) 	responseMap.get("updatedNo")		== null ? null : (Integer) 	responseMap.get("updatedNo");
			this.details			= (List<MemberDetailDto.ViewMemberDetailRes>) responseMap.get("details") == null ? null : (List<MemberDetailDto.ViewMemberDetailRes>) responseMap.get("details");
		}
	}
	
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewMembersRes {
        private List<ViewMemberRes> members;
        private Integer totalCount;

        public ViewMembersRes(List<ViewMemberRes> members, Integer totalCount) {
            this.members = members;
            this.totalCount = totalCount;
        }
    }

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewMemberReq {
		private String id;
		private String name;
		private String role;
		private String email;
		private String phone;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String delYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String socialYn;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}    
    
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AddMemberReq {
		private Integer memberSeq;
		@NotEmpty(message = "아이디 값이 없습니다.")
		private String id;
		@NotEmpty(message = "패스워드 값이 없습니다.")
		private String password;
		@NotEmpty(message = "이름 값이 없습니다.")
		private String name;
		@NotEmpty(message = "유저유형 값이 없습니다.")
		private String role;
		private String profile;
		@Email(message = "이메일 값이 유효하지 않습니다.")
		private String email;
		@NotEmpty(message = "핸드폰 값이 없습니다.")
		private String phone;
		private String addr;
		private String remark;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String socialYn;
		private List<MemberDetailDto.AddMemberDetailReq> details;
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SetMemberReq {
		@NotNull(message = "유저고유번호 값이 없습니다.")
		private Integer memberSeq;
		@NotEmpty(message = "이름 값이 없습니다.")
		private String name;
		@NotEmpty(message = "유저유형 값이 없습니다.")
		private String role;
		private String profile;
		@Email(message = "이메일 값이 유효하지 않습니다.")
		private String email;
		@NotEmpty(message = "핸드폰 값이 없습니다.")
		private String phone;
		private String addr;
		private String remark;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String socialYn;
		private List<MemberDetailDto.AddMemberDetailReq> details;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class LogicalRemoveMemberReq {
		@NotNull(message = "유저고유번호 값이 없습니다.")
		private Integer memberSeq;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String delYn;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}
}
