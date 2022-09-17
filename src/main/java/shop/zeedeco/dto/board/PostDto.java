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

public class PostDto {
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewPostRes {
		private Integer postSeq;
		private Integer boardFk;
		private Integer memberFk;
		private String thumbnail;
		private String title;
		private String content;
		private String remark;
		private LocalDateTime startDt;
		private LocalDateTime endDt;
		private String noticeYn;
		private String secretYn;
		private String useYn;
		private String delYn;
		private Integer viewCount;
		private LocalDateTime createdDt;
		private Integer createdNo;
		private LocalDateTime updatedDt;
		private Integer updatedNo;
		
		public ViewPostRes(Map<String, Object> responseMap) {
			this.postSeq			= (Integer) 	responseMap.get("postSeq") 			== null ? null : (Integer) 	responseMap.get("postSeq");
			this.boardFk			= (Integer) 	responseMap.get("boardFk") 			== null ? null : (Integer) 	responseMap.get("boardFk");
			this.memberFk			= (Integer) 	responseMap.get("memberFk") 		== null ? null : (Integer) 	responseMap.get("memberFk");
			this.thumbnail			= (String)		responseMap.get("thumbnail")		== null ? null : (String)	responseMap.get("thumbnail");
			this.title				= (String) 		responseMap.get("title") 		 	== null ? null : (String) 	responseMap.get("title");
			this.content			= (String) 		responseMap.get("content")  		== null ? null : (String) 	responseMap.get("content");
			this.remark				= (String) 		responseMap.get("remark")			== null ? null : (String) 	responseMap.get("remark");
			this.startDt			= (LocalDateTime) 		responseMap.get("startDt")	== null ? null : (LocalDateTime) 	responseMap.get("startDt");
			this.endDt				= (LocalDateTime) 		responseMap.get("endDt")	== null ? null : (LocalDateTime) 	responseMap.get("endDt");
			this.noticeYn			= (String) 		responseMap.get("noticeYn")			== null ? null : (String) 	responseMap.get("noticeYn");
			this.secretYn			= (String) 		responseMap.get("secretYn")			== null ? null : (String) 	responseMap.get("secretYn");
			this.useYn				= (String) 		responseMap.get("useYn")			== null ? null : (String) 	responseMap.get("useYn");
			this.delYn				= (String) 		responseMap.get("delYn")			== null ? null : (String) 	responseMap.get("delYn");
			this.viewCount			= (Integer) 	responseMap.get("viewCount")		== null ? null : (Integer) 	responseMap.get("viewCount");
			this.createdDt			= (LocalDateTime) 	responseMap.get("createdDt") 	== null ? null : (LocalDateTime) 	responseMap.get("createdDt");
			this.createdNo			= (Integer) 	responseMap.get("createdNo")		== null ? null : (Integer) 	responseMap.get("createdNo");
			this.updatedDt			= (LocalDateTime) 	responseMap.get("updatedDt") 	== null ? null : (LocalDateTime) 	responseMap.get("updatedDt");
			this.updatedNo			= (Integer) 	responseMap.get("updatedNo")		== null ? null : (Integer) 	responseMap.get("updatedNo");
		}
	}
	
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewPostsRes {
        private List<PostDto.ViewPostRes> posts;
        private Integer totalCount;

        public ViewPostsRes(List<PostDto.ViewPostRes> posts, Integer totalCount) {
            this.posts 		= posts;
            this.totalCount = totalCount;
        }
    }
 
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MessageRes {
        private String message;

        public MessageRes( String message ) {
            this.message 	= message;
        }
    }
    
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewPostReq {
		private Integer boardFk;
		private String title;
		private String content;
		private String remark;
		private String startDt;
		private String endDt;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String noticeYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String secretYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String useYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String delYn;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	} 
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AddPostReq {
		@NotNull(message = "게시판 고유번호가 비어있습니다.")
		private Integer boardFk;
		@NotNull(message = "유저 고유번호가 비어있습니다.")
		private Integer memberSeq;
		private String thumbnail;
		@NotEmpty(message = "게시글 제목이 비어있습니다.")
		private String title;
		@NotEmpty(message = "게시글 정보가 비어있습니다.")
		private String content;
		private String remark;
		private String startDt;
		private String endDt;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String noticeYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String secretYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String useYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String delYn;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}   
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SetPostReq {
		@NotNull(message = "게시글 고유번호가 비어있습니다.")
		private Integer postSeq;
		@NotNull(message = "유저 고유번호가 비어있습니다.")
		private Integer memberSeq;
		private String thumbnail;
		@NotEmpty(message = "게시글 제목이 비어있습니다.")
		private String title;
		@NotEmpty(message = "게시글 정보가 비어있습니다.")
		private String content;
		private String remark;
		private String startDt;
		private String endDt;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String noticeYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String secretYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String useYn;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}   
		
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SetPostForViewCountReq {
		@NotNull(message = "게시글 고유번호가 비어있습니다.")
		private Integer postSeq;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}   
}
