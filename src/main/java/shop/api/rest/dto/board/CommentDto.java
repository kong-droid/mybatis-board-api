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
	
    @Schema(description = "��� ������ȣ", example = "1", required = false)
    @Positive(message = "commentSeq�� ������� �մϴ�.")
    private Integer commentSeq;
    
    @Schema(description = "�Խñ� ������ȣ", example = "1", required = true)
    @Positive(message = "postSeq�� ������� �մϴ�.")
    private Integer postSeq;
    
    @Schema(description = "�θ� ��� ������ȣ", example = "1", required = true)
    @PositiveOrZero(message = "parentsNo�� 0�̰ų� ������� �մϴ�.")
    private Integer parentsNo;
    
    @Schema(description = "��� ����", example = "1", required = true)
    @Positive(message = "depsNo�� ������� �մϴ�.")
    private Integer depsNo;
    
    @Schema(description = "���", example = "��� ������ �Է��ϼ���", required = true)
    private String comment;
    
    private CommonHandleDto handle;
    
    private CommonSearchDto search;
}
