package shop.zeedeco.dto.memo;

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
@Schema(description = "�޸�")
public class MemoDto {

    @Schema(description = "�޸� ������ȣ", example = "1", required = true)
    @Positive(message = "memoSeq�� ������� �մϴ�.")
    private Integer memoSeq;
    
    @Schema(description = "�޸��Ͻ�", example = "20220101", required = false)
    private String memoDt;
        
    @Schema(description = "���� �Է�/����/���� �Ķ����", required = false)
    private CommonHandleDto handle;
    
    @Schema(description = "���� �˻� �Ķ����", required = false)
    private CommonSearchDto search;
    
}
