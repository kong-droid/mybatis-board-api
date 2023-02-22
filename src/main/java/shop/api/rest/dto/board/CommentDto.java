package shop.api.rest.dto.board;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
@Schema(description = "Comment")
public class CommentDto {
	
    @Schema(description = "댓글 고유번호", example = "1", required = false)
    @Positive(message = "commentSeq는 양수여야 합니다.")
    private Integer commentSeq;
    
    @Schema(description = "게시글 고유번호", example = "1", required = true)
    @Positive(message = "postSeq는 양수여야 합니다.")
    private Integer postSeq;
    
    @Schema(description = "부모 댓글 고유번호", example = "1", required = true)
    @PositiveOrZero(message = "parentsNo는 0이거나 양수여야 합니다.")
    private Integer parentsNo;
    
    @Schema(description = "댓글 뎁스", example = "1", required = true)
    @Positive(message = "depsNo는 양수여야 합니다.")
    private Integer depsNo;
    
    @Schema(description = "댓글", example = "댓글 내용을 입력하세요", required = true)
    private String comment;
    
    private CommonHandleDto handle;
    
    private CommonSearchDto search;
}
