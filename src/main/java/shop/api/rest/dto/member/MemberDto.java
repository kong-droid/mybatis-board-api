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
@Schema(description = "ȸ������")
public class MemberDto {
	
    @Schema(description = "ȸ�� ���̵�", example = "tester", required = true)
    private String id;
    
    @Schema(description = "ȸ�� ��й�ȣ", example = "1234", required = true)
    private String password;
    
    @Schema(description = "ȸ����", example = "�׽���", required = true)
    private String name;
    
    @Schema(description = "<h3>ȸ������</h3><br>�Ϲ�ȸ�� : ROLE_USER<br>������ : ROLE_ADMIN", example = "ROLE_ADMIN", required = true)
    private String role;
    
    @Schema(description = "ȸ�� ������", example = "data/profile/20220101/profile.jpg", required = false)
    private String profile;
    
    @Schema(description = "�̸���", example = "tester@gmail.com", required = true)
    @Email(message = "email ������ �����ʽ��ϴ�.")
    private String email;
    
    @Schema(description = "����Ϲ�ȣ", example = "01000001234", required = false)
    private String phone;
    
    @Schema(description = "�ּ�", example = "����Ư���� �߱� ���", required = false)
    private String addr;
    
    private CommonHandleDto handle;
    
    private CommonSearchDto search;
    
    List<MemberDetailDto> details;
}
