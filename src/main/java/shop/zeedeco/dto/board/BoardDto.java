package shop.zeedeco.dto.board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewBoardRes {
		private Integer boardSeq;
		private String title;
		private String content;
		private String remark;
		private LocalDateTime startDt;
		private LocalDateTime endDt;
		private String commentYn;
		private String useYn;
		private String delYn;
		private LocalDateTime createdDt;
		private Integer createdNo;
		private LocalDateTime updatedDt;
		private Integer updatedNo;
		
		public ViewBoardRes(Map<String, Object> responseMap) {
			this.boardSeq			= (Integer) 	responseMap.get("boardSeq") 		== null ? null : (Integer) 	responseMap.get("boardSeq");
			this.title				= (String) 		responseMap.get("title") 		 	== null ? null : (String) 	responseMap.get("title");
			this.content			= (String) 		responseMap.get("content")  		== null ? null : (String) 	responseMap.get("content");
			this.remark				= (String) 		responseMap.get("remark")			== null ? null : (String) 	responseMap.get("remark");
			this.startDt			= (LocalDateTime) 		responseMap.get("startDt")	== null ? null : (LocalDateTime) 	responseMap.get("startDt");
			this.endDt				= (LocalDateTime) 		responseMap.get("endDt")	== null ? null : (LocalDateTime) 	responseMap.get("endDt");
			this.commentYn			= (String) 		responseMap.get("commentYn")		== null ? null : (String) 	responseMap.get("commentYn");
			this.useYn				= (String) 		responseMap.get("useYn")			== null ? null : (String) 	responseMap.get("useYn");
			this.delYn				= (String) 		responseMap.get("delYn")			== null ? null : (String) 	responseMap.get("delYn");
			this.createdDt			= (LocalDateTime) 	responseMap.get("createdDt") 	== null ? null : (LocalDateTime) 	responseMap.get("createdDt");
			this.createdNo			= (Integer) 	responseMap.get("createdNo")		== null ? null : (Integer) 	responseMap.get("createdNo");
			this.updatedDt			= (LocalDateTime) 	responseMap.get("updatedDt") 	== null ? null : (LocalDateTime) 	responseMap.get("updatedDt");
			this.updatedNo			= (Integer) 	responseMap.get("updatedNo")		== null ? null : (Integer) 	responseMap.get("updatedNo");
		}
	}
	
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewBoardsRes {
        private List<BoardDto.ViewBoardRes> boards;
        private Integer totalCount;

        public ViewBoardsRes(List<BoardDto.ViewBoardRes> boards, Integer totalCount) {
            this.boards 	= boards;
            this.totalCount = totalCount;
        }
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class HandleBoardRes {
        private Integer boardSeq;

        public HandleBoardRes( Integer boardSeq ) {
            this.boardSeq 	= boardSeq;
        }
    }
    
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewBoardReq {
		private String title;
		private String content;
		private String keyword;
		private String startedStartDt;
		private String endedStartDt;
		private String startedEndDt;
		private String endedEndDt;
		private String createdStartDt;
		private String createdEndDt;
		private String updatedStartDt;
		private String updatedEndDt;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String commentYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String useYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String delYn;
	}  
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class HandleBoardReq {
		@NotEmpty(message = "게시판 제목이 비어있습니다.")
		private String title;
		@NotEmpty(message = "게시판 정보가 비어있습니다.")
		private String content;
		private String remark;
		private String startDt;
		private String endDt;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String commentYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String useYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String delYn;
		@NotNull(message = "유저 고유번호가 비어있습니다.")
		private int memberSeq;
	}   
		
}
