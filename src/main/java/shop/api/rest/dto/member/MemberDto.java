package shop.api.rest.dto.member;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.api.rest.dto.common.CommonHandleDto;
import shop.api.rest.dto.common.CommonSearchDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Schema(description = "회원정보")
public class MemberDto {
	
    @Schema(description = "회원 아이디", example = "tester", required = true)
    private String id;
    
    @Schema(description = "회원 비밀번호", example = "1234", required = true)
    private String password;
    
    @Schema(description = "회원명", example = "테스터", required = true)
    private String name;
    
    @Schema(description = "<h3>회원유형</h3><br>일반회원 : ROLE_USER<br>관리자 : ROLE_ADMIN", example = "ROLE_ADMIN", required = true)
    private String role;
    
    @Schema(description = "회원 프로필", example = "data/profile/20220101/profile.jpg", required = false)
    private String profile;
    
    @Schema(description = "이메일", example = "tester@gmail.com", required = true)
    @Email(message = "email 형식이 맞지않습니다.")
    private String email;
    
    @Schema(description = "모바일번호", example = "01000001234", required = false)
    private String phone;
    
    @Schema(description = "주소", example = "서울특별시 중구 어딘가", required = false)
    private String addr;
    
    private CommonHandleDto handle;
    
    private CommonSearchDto search;
    
    List<MemberDetailDto> details;
}
