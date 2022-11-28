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
@Schema(description = "메모")
public class MemoDto {

    @Schema(description = "메모 고유번호", example = "1", required = true)
    @Positive(message = "memoSeq는 양수여야 합니다.")
    private Integer memoSeq;
    
    @Schema(description = "메모일시", example = "20220101", required = false)
    private String memoDt;
    
    private CommonHandleDto handle;
    
    private CommonSearchDto search;
    
}
