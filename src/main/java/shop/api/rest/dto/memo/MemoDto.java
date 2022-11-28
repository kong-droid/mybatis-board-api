package shop.api.rest.dto.memo;

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
@Schema(description = "�޸�")
public class MemoDto {

    @Schema(description = "�޸� ������ȣ", example = "1", required = true)
    @Positive(message = "memoSeq�� ������� �մϴ�.")
    private Integer memoSeq;
    
    @Schema(description = "�޸��Ͻ�", example = "20220101", required = false)
    private String memoDt;
    
    private CommonHandleDto handle;
    
    private CommonSearchDto search;
    
}
