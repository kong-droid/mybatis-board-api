package site.kongdroid.api.dto.board;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.kongdroid.api.dto.common.CommonHandleDto;
import site.kongdroid.api.dto.common.CommonSearchDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Schema(description = "Post")
public class PostDto {
	
    @Schema(description = "게시글 고유번호", required = true)
    @Positive(message = "postSeq는 양수여야 합니다.")
    private Integer postSeq;
    
    @Schema(description = "게시판 고유번호", required = true)
    @Positive(message = "boardSeq는 양수여야 합니다.")
    private Integer boardSeq;
    
    @Schema(description = "게시글 썸네일", required = false)
    private String thumbnail;
    
    @Schema(description = "게시글 제목", required = true)
    private String title;
    
    @Schema(description = "공지사항 여부", required = false)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
    private String noticeYn;
    
    @Schema(description = "비밀글 여부", required = false)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
    private String secretYn;
    
    private CommonHandleDto handle;
    
    private CommonSearchDto search;
}
