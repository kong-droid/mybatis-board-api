package site.kongdroid.api.dto.member;

import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.kongdroid.api.dto.common.CommonHandleDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Schema(description = "회원정보 상세")
public class MemberDetailDto {

    @Schema(description = "회원정보 상세 고유번호", example = "1", required = true)
    @Positive(message = "detailSeq는 양수여야 합니다.")
    private Integer detailSeq;
    
    @Schema(description = "self_introduce", example = "자기소개를 입력하세요.", required = true)
    private String category;

    private CommonHandleDto handle;
   
}
