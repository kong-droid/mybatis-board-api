package shop.api.rest.dto.attach;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.web.multipart.MultipartFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class AttachDto {
	
    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewAttachRes {
        private Integer attachSeq;
        private String realName;
        private String uuidName;
        private String fileType;
        private String fileSize;
        private String filePath;
        private String fullPath;
        private String tbName;
        private Integer tbSeq;
        private String tbType;
        private Integer createdNo;
        private LocalDateTime createdDt;

        public ViewAttachRes(Map<String, Object> responseMap) {
            this.attachSeq	= (Integer) responseMap.get("attachSeq") 		== null ? null : (Integer) responseMap.get("attachSeq");
            this.realName	= (String) responseMap.get("realName")			== null ? null : (String) responseMap.get("realName");
            this.uuidName   = (String) responseMap.get("uuidName")			== null ? null : (String) responseMap.get("uuidName");
            this.fileType	= (String) responseMap.get("fileType")			== null ? null : (String) responseMap.get("fileType");
            this.fileSize	= (String) responseMap.get("fileSize")			== null ? null : (String) responseMap.get("fileSize");
            this.filePath	= (String) responseMap.get("filePath")      	== null ? null : (String) responseMap.get("filePath");
            this.fullPath	= (String) responseMap.get("fullPath")			== null ? null : (String) responseMap.get("fullPath");
            this.tbName     = (String) responseMap.get("tbName")			== null ? null : (String) responseMap.get("tbName");
            this.tbSeq      = (Integer) responseMap.get("tbSeq")			== null ? null : (Integer) responseMap.get("tbSeq");
            this.tbType     = (String) responseMap.get("tbType")			== null ? null : (String) responseMap.get("tbType");
            this.createdNo  = (Integer) responseMap.get("createdNo")		== null ? null : (Integer) responseMap.get("createdNo");
            this.createdDt  = (LocalDateTime) responseMap.get("createdDt")  == null ? null : (LocalDateTime) responseMap.get("createdDt");
       }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class ViewAttachesRes {
        private List<AttachDto.ViewAttachRes> attaches;

        public ViewAttachesRes(List <AttachDto.ViewAttachRes> attaches) {
            this.attaches       = attaches;
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewAddAttachRes {
        private Integer attachSeq;
        private String fullPath;
        
        public ViewAddAttachRes(Map<String, Object> responseMap) {
            this.attachSeq	= (Integer) responseMap.get("attachSeq") 		== null ? null : (Integer) responseMap.get("attachSeq");
            this.fullPath	= (String) responseMap.get("fullPath")			== null ? null : (String) responseMap.get("fullPath");
       }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class ViewAddAttachesRes {
        private List<AttachDto.ViewAddAttachRes> attached;

        public ViewAddAttachesRes(List <AttachDto.ViewAddAttachRes> attached) {
            this.attached       = attached;
        }
    }
    
    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewAttachReq {
        private String tbName;
        private Integer tbSeq;
        private String tbType;
    }

    @ToString
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddAttachReq {
        @NotEmpty(message = "tbName이 비어있음. ")
    	private String tbName;
        @NotEmpty(message = "tbSeq이 비어있음. ")
        private String tbSeq;
        @NotEmpty(message = "tbType이 비어있음. ")
        private String tbType;
        @NotNull(message = "memberSeq가 비어있음. ")
        @Positive(message = "memberSeq는 양수여야함. ")
        private Integer memberSeq;
        @NotNull(message = "files가 비어있음. ")
        private List<MultipartFile> files;
    }

}
