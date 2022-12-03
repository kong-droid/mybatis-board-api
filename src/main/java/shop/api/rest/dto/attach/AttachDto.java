package shop.api.rest.dto.attach;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class AttachDto {

    
    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewAttachReq {
        
        @Schema(description = "����÷�� ���� ���̺�", example = "post")
        private String tbName;
        
        @Schema(description = "����÷�� ���� ���̺� ������ȣ", example = "10")
        private Integer tbSeq;
        
        @Schema(description = "����÷�� ���� Ÿ��", example = "post contents image")
        private String tbType;
    }

    @ToString
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddAttachReq {
        
        @NotEmpty(message = "tbName�� �������. ")
    	@Parameter(description = "����÷�� ���� ���̺�", example = "post")
        private String tbName;
        
        @NotEmpty(message = "tbSeq�� �������. ")
        @Parameter(description = "����÷�� ���� ���̺� ������ȣ", example = "10")
        private String tbSeq;
        
        @NotEmpty(message = "tbType�� �������. ")
        @Parameter(description = "����÷�� ���� Ÿ��", example = "post contents image")
        private String tbType;
        
        @NotNull(message = "memberSeq�� �������. ")
        @Positive(message = "memberSeq�� ���������. ")
        @Parameter(description = "ȸ�� ������ȣ", example = "1")
        private Integer memberSeq;
        
        @NotNull(message = "files�� �������. ")
        private List<MultipartFile> files;
    }

}
