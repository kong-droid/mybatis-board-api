package shop.api.rest.dto.member;

import javax.validation.constraints.Positive;

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
@Schema(description = "ȸ������ ��")
public class MemberDetailDto {

    @Schema(description = "ȸ������ �� ������ȣ", example = "1", required = true)
    @Positive(message = "detailSeq�� ������� �մϴ�.")
    private Integer detailSeq;
    
    @Schema(description = "self_introduce", example = "�ڱ�Ұ��� �Է��ϼ���.", required = true)
    private String category;

    private CommonHandleDto handle;
   
}
