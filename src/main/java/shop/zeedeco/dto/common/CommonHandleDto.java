package shop.zeedeco.dto.common;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
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
@Schema(description = "공통 입력/수정/삭제 파라미터")
public class CommonHandleDto {
    
    @Schema(description = "언어코드", example = "KR", required = false)
    private String langCd;
    
    @Schema(description = "타입코드", example = "notice", required = false)
    private String typeCd;
    
    @Schema(description = "내용", example = "내용을 입력해보세요.", required = true)
    private String content;
    
    @Schema(description = "비고", example = "비고를 입력할 일이 있을까요?", required = false)
    private String remark;
    
    @Schema(description = "게시 시작일", example = "2022-01-01 00:00:00", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodStartDt;

    @Schema(description = "게시 종료일", example = "2022-12-31 23:59:59", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodEndDt;
    
    @Schema(description = "사용여부", example = "Y", required = true)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
    private String useYn;
    
    @Schema(description = "삭제여부", example = "N", required = true)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
    private String delYn;

    @Schema(description = "유저 고유번호", example = "1", required = true)
    @Positive(message = "memberSeq는 양수여야 합니다.")
    private Integer memberSeq;
    
}
