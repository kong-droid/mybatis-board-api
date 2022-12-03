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
        
        @Schema(description = "파일첨부 참조 테이블", example = "post")
        private String tbName;
        
        @Schema(description = "파일첨부 참조 테이블 고유번호", example = "10")
        private Integer tbSeq;
        
        @Schema(description = "파일첨부 참조 타입", example = "post contents image")
        private String tbType;
    }

    @ToString
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddAttachReq {
        
        @NotEmpty(message = "tbName이 비어있음. ")
    	@Parameter(description = "파일첨부 참조 테이블", example = "post")
        private String tbName;
        
        @NotEmpty(message = "tbSeq이 비어있음. ")
        @Parameter(description = "파일첨부 참조 테이블 고유번호", example = "10")
        private String tbSeq;
        
        @NotEmpty(message = "tbType이 비어있음. ")
        @Parameter(description = "파일첨부 참조 타입", example = "post contents image")
        private String tbType;
        
        @NotNull(message = "memberSeq가 비어있음. ")
        @Positive(message = "memberSeq는 양수여야함. ")
        @Parameter(description = "회원 고유번호", example = "1")
        private Integer memberSeq;
        
        @NotNull(message = "files가 비어있음. ")
        private List<MultipartFile> files;
    }

}
