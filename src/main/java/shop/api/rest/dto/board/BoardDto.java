package shop.api.rest.dto.board;

import javax.validation.constraints.Pattern;
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
@Schema(description = "Board")
public class BoardDto {
    
    @Schema(description = "게시판 고유번호", example = "1", required = false)
    @Positive(message = "boardSeq는 양수여야 합니다.")
    private Integer boardSeq;

    @Schema(description = "게시판 제목", example = "공지사항", required = true)
    private String title;
    
    @Schema(description = "게시판 파일첨부여부", example = "N", required = true)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
    private String attachmentYn;
    
    @Schema(description = "댓글여부", example = "Y", required = true)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
    private String commentYn;
        
    private CommonHandleDto handle;
    
    private CommonSearchDto search;
	
}
