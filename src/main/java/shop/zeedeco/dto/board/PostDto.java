package shop.zeedeco.dto.board;

import javax.validation.constraints.Pattern;
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
@Schema(description = "Post")
public class PostDto {
	
    @Schema(description = "�Խñ� ������ȣ", required = true)
    @Positive(message = "postSeq�� ������� �մϴ�.")
    private Integer postSeq;
    
    @Schema(description = "�Խ��� ������ȣ", required = true)
    @Positive(message = "boardSeq�� ������� �մϴ�.")
    private Integer boardSeq;
    
    @Schema(description = "�Խñ� �����", required = false)
    private String thumbnail;
    
    @Schema(description = "�Խñ� ����", required = true)
    private String title;
    
    @Schema(description = "�������� ����", required = false)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' �Ǵ� 'N' �� �ԷµǾ�� �մϴ�.")
    private String noticeYn;
    
    @Schema(description = "��б� ����", required = false)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' �Ǵ� 'N' �� �ԷµǾ�� �մϴ�.")
    private String secretYn;
    
    @Schema(description = "���� �Է�/����/���� �Ķ����", required = false)
    private CommonHandleDto handle;
    
    @Schema(description = "���� �ϻ� �Ķ����", required = false)
    private CommonSearchDto search;
}
