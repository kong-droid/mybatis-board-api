package site.kongdroid.api.dto.request.auth;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Schema(description = "회원정보")
public class AuthDto {

    @Schema(description = "유저 고유번호", example = "1", required = true)
    @Positive(message = "memberSeq는 양수여야 합니다.")
    private Integer memberSeq;
    
    @Schema(description = "유저 아이디", example = "admin")
    private String id;
    
    @Schema(description = "비밀번호", example = "amugae1234$%")
    @Pattern(message = "비밀번호는 영소문자, 숫자, 특수문자(!@#$%^&*)를 포함한 6~20자이내여야 합니다.", regexp = "^([0-9A-Za-z!@#$%^&*]{6,20})$")
    private String password;
    
    @Schema(description = "새 비밀번호", example = "amugae1234$%")
    @Pattern(message = "비밀번호는 영소문자, 숫자, 특수문자(!@#$%^&*)를 포함한 6~20자이내여야 합니다.", regexp = "^([0-9A-Za-z!@#$%^&*]{6,20})$")
    private String newPassword;

    @Schema(description = "기존 비밀번호", example = "amugae5678!@")
    @Pattern(message = "비밀번호는 영소문자, 숫자, 특수문자(!@#$%^&*)를 포함한 6~20자이내여야 합니다.", regexp = "^([0-9A-Za-z!@#$%^&*]{6,20})$")
    private String oldPassword;
    
}
