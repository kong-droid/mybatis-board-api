package shop.zeedeco.dto.board;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

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
@Schema(description = "Board")
public class BoardDto {
    
    @Schema(description = "�Խ��� ������ȣ", example = "1", required = false)
    @Positive(message = "boardSeq�� ������� �մϴ�.")
    private Integer boardSeq;

    @Schema(description = "�Խ��� ����", example = "��������", required = true)
    private String title;
    
    @Schema(description = "�Խ��� ����÷�ο���", example = "N", required = true)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' �Ǵ� 'N' �� �ԷµǾ�� �մϴ�.")
    private String attachmentYn;
    
    @Schema(description = "��ۿ���", example = "Y", required = true)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' �Ǵ� 'N' �� �ԷµǾ�� �մϴ�.")
    private String commentYn;
        
    private CommonHandleDto handle;
    
    private CommonSearchDto search;
	
}
