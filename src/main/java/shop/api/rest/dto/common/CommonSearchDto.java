package shop.api.rest.dto.common;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
@Schema(description = "���� �˻� �Ķ����")
public class CommonSearchDto {
    
    @Schema(description = "������", example = "0", required = false)
    @PositiveOrZero(message = "page�� ������� �մϴ�.")
    private Integer page;

    @Schema(description = "������ ������", example = "20", required = false)
    @Positive(message = "size�� ������� �մϴ�.")
    private Integer size;
    
    @Schema(description = "����", example = "������ �Է��غ�����.", required = true)
    private String content;
    
    @Schema(description = "���", example = "��� �Է��� ���� �������?", required = false)
    private String remark;
    
    @Schema(description = "���հ˻� Ű����", example = "�˻�� �Է��غ�����.", required = false)
    private String keyword;
    
    @Schema(description = "����ڵ�", example = "KR", required = false)
    private String langCd;
    
    @Schema(description = "Ÿ���ڵ�", example = "notice", required = false)
    private String typeCd;
    
    @Schema(description = "�Խ� ������ ���۹���", example = "2022-01-01 00:00:00", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodStartedStartDt;
    
    @Schema(description = "�Խ� ������ �������", example = "2022-12-31 23:59:59", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodEndedStartDt;
    
    @Schema(description = "�Խ� ������ ���۹���", example = "2022-01-01 00:00:00", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodStartedEndDt;
    
    @Schema(description = "�Խ� ������ �������", example = "2022-12-31 23:59:59", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodEndedEndDt;
    
    @Schema(description = "����� �˻� ���۹���", example = "2022-01-01 00:00:00", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String createdStartDt;
    
    @Schema(description = "����� �˻� �������", example = "2022-12-31 23:59:59", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String createdEndDt;
    
    @Schema(description = "������ �˻� ���۹���", example = "2022-01-01 00:00:00", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String updatedStartDt;
    
    @Schema(description = "������ �˻� �������", example = "2022-12-31 23:59:59", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String updatedEndDt;
        
    @Schema(description = "�Խ� ������", example = "2022-01-01 00:00:00", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodStartDt;

    @Schema(description = "�Խ� ������", example = "2022-12-31 23:59:59", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd tt:mm:hh", iso = DateTimeFormat.ISO.DATE_TIME)
    private String periodEndDt;
    
    @Schema(description = "��뿩��", example = "Y", required = false)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' �Ǵ� 'N' �� �ԷµǾ�� �մϴ�.")
    private String useYn;
    
    @Schema(description = "��������", example = "N", required = false)
    @Pattern(regexp = "^(Y|N)$", message = "'Y' �Ǵ� 'N' �� �ԷµǾ�� �մϴ�.")
    private String delYn;

    @Schema(description = "���� ������ȣ", example = "1", required = false)
    @Positive(message = "memberSeq�� ������� �մϴ�.")
    private Integer memberSeq;
    
}
