package shop.zeedeco.dto.member;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.zeedeco.dto.common.CommonHandleDto;
import shop.zeedeco.dto.common.CommonSearchDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Schema(description = "회원정보")
public class MemberDto {
	
    @Schema(description = "회원 아이디", example = "tester", required = true)
    private String id;
    
    @Schema(description = "회원명", example = "테스터", required = true)
    private String name;
    
    @Schema(description = "회원 유형", example = "<h3>회원유형</h3><br>일반회원 : ROLE_USER<br>관리자 : ROLE_ADMIN", required = true)
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
    
    @Schema(description = "소셜 로그인 사용여부", required = false)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
    private String socialYn;
    
    @Schema(description = "공통 입력/수정/삭제 파라미터", required = false)
    private CommonHandleDto handle;
    
    @Schema(description = "공통 검색 파라미터", required = false)
    private CommonSearchDto search;
    
    List<MemberDetailDto> details;
}
