package site.kongdroid.api.dto.request.memo;

import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.kongdroid.api.dto.request.common.CommonHandleDto;
import site.kongdroid.api.dto.request.common.CommonSearchDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Schema(description = "메모")
public class MemoDto {

    @Positive(message = "memoSeq는 양수여야 합니다.")
    @Schema(description = "메모일시", example = "20220101", required = false)
    Integer memoSeq;

    @Schema(description = "메모일시", example = "20220101", required = false)
    private String memoDt;
    
    private CommonHandleDto handle;
    
    private CommonSearchDto search;
    
}
