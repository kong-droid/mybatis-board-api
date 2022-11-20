package shop.zeedeco.dto.auth;

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
@Schema(description = "ȸ������")
public class AuthDto {

    @Schema(description = "���� ������ȣ", example = "1", required = true)
    @Positive(message = "memberSeq�� ������� �մϴ�.")
    private Integer memberSeq;
    
    @Schema(description = "���� ���̵�", example = "admin")
    private String id;
    
    @Schema(description = "��й�ȣ", example = "amugae1234$%")
    @Pattern(message = "��й�ȣ�� ���ҹ���, ����, Ư������(!@#$%^&*)�� ������ 6~20���̳����� �մϴ�.", regexp = "^([0-9a-z!@#$%^&*]{6,20})$")
    private String password;
    
    @Schema(description = "�� ��й�ȣ", example = "amugae1234$%")
    @Pattern(message = "��й�ȣ�� ���ҹ���, ����, Ư������(!@#$%^&*)�� ������ 6~20���̳����� �մϴ�.", regexp = "^([0-9a-z!@#$%^&*]{6,20})$")
    private String newPassword;

    @Schema(description = "���� ��й�ȣ", example = "amugae5678!@")
    @Pattern(message = "��й�ȣ�� ���ҹ���, ����, Ư������(!@#$%^&*)�� ������ 6~20���̳����� �մϴ�.", regexp = "^([0-9a-z!@#$%^&*]{6,20})$")
    private String oldPassword;
    
}
