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
@Schema(description = "메모")
public class MemoDto {

    @Schema(description = "메모 고유번호", example = "1", required = true)
    @Positive(message = "memoSeq는 양수여야 합니다.")
    private Integer memoSeq;
    
    @Schema(description = "메모일시", example = "20220101", required = false)
    private String memoDt;
        
    @Schema(description = "공통 입력/수정/삭제 파라미터", required = false)
    private CommonHandleDto handle;
    
    @Schema(description = "공통 검색 파라미터", required = false)
    private CommonSearchDto search;
    
}
