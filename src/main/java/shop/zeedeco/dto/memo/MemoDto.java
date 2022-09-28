package shop.zeedeco.dto.memo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class MemoDto {
   
	@Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewMemoRes {
        private Integer memoSeq;
        private Integer memberSeq;
        private String memoDate;
        private String content;
        private String remark;
        private Integer createdNo;
        private LocalDateTime createdDt;
        private Integer updatedNo;
        private LocalDateTime updatedDt;
        
        public ViewMemoRes(Map<String, Object> responseMap) {
            this.memoSeq	= (Integer) responseMap.get("memoSeq") 			== null ? null : (Integer) responseMap.get("memoSeq");
            this.memberSeq	= (Integer) responseMap.get("memberSeq") 		== null ? null : (Integer) responseMap.get("memberSeq");
            this.memoDate   = (String) responseMap.get("memoDate")			== null ? null : (String) responseMap.get("memoDate");
            this.content	= (String) responseMap.get("content")			== null ? null : (String) responseMap.get("content");
            this.remark		= (String) responseMap.get("remark")			== null ? null : (String) responseMap.get("remark");
            this.createdNo  = (Integer) responseMap.get("createdNo")		== null ? null : (Integer) responseMap.get("createdNo");
            this.createdDt  = (LocalDateTime) responseMap.get("createdDt") == null ? null : (LocalDateTime) responseMap.get("createdDt");
            this.updatedNo  = (Integer) responseMap.get("updatedNo")		== null ? null : (Integer) responseMap.get("updatedNo");
            this.updatedDt  = (LocalDateTime) responseMap.get("updatedDt") == null ? null : (LocalDateTime) responseMap.get("updatedDt");
        } 
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewMemosRes {
        private List<MemoDto.ViewMemoRes> memos;

        public ViewMemosRes(List <MemoDto.ViewMemoRes> memos) {
            this.memos       = memos;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class HandleMemoRes {
    	private Integer memoSeq;
    	
        public HandleMemoRes(Integer memoSeq) {
            this.memoSeq = memoSeq;
        }
        
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewMemoReq {
        private Integer memberSeq;
        private String memoDate;
        private String content;
        private String createdStartDt;
        private String createdEndDt;
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class HandleMemoReq {
    	@NotNull(message = "���� ������ȣ ���� �����ϴ�.")
        private Integer memberSeq;
    	@NotEmpty(message = "�޸� �ۼ��� ���� �����ϴ�.")
        private String memoDate;
    	@NotEmpty(message = "�޸� ���� ���� �����ϴ�.")
        private String content;
        private String remark;

    }

}
