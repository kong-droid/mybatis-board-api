package shop.zeedeco.dto.board;

import javax.validation.constraints.Pattern;

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
    
    @Schema(description = "게시판 고유번호", example = "1", required = false)
    private Integer boardSeq;

    @Schema(description = "게시판 제목", example = "공지사항", required = true)
    private String title;
    
    @Schema(description = "게시판 파일첨부여부", example = "N", required = true)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
    private String attachmentYn;
    
    @Schema(description = "댓글여부", example = "Y", required = true)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
    private String commentYn;
        
    @Schema(description = "공통 입력/수정/삭제 파라미터", required = false)
    private CommonHandleDto handle;
    
    @Schema(description = "공통 겅샘 파라미터", required = false)
    private CommonSearchDto search;
	
}
