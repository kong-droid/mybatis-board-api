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

public class CommentDto {
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewCommentRes {
		private Integer commentSeq;
		private Integer postFk;
		private Integer memberFk;
		private Integer parents;
		private Integer deps;
		private String comment;
		private LocalDateTime createdDt;
		private Integer createdNo;
		private LocalDateTime updatedDt;
		private Integer updatedNo;
		
		public ViewCommentRes(Map<String, Object> responseMap) {
			this.commentSeq			= (Integer) 	responseMap.get("commentSeq") 			== null ? null : (Integer) 	responseMap.get("commentSeq");
			this.postFk				= (Integer) 	responseMap.get("postFk") 				== null ? null : (Integer) 	responseMap.get("postFk");
			this.memberFk			= (Integer) 	responseMap.get("memberFk") 			== null ? null : (Integer) 	responseMap.get("memberFk");
			this.parents			= (Integer) 	responseMap.get("parents") 		 		== null ? null : (Integer) 	responseMap.get("parents");
			this.deps				= (Integer) 	responseMap.get("deps")  				== null ? null : (Integer) 	responseMap.get("deps");
			this.comment			= (String) 		responseMap.get("comment")				== null ? null : (String) 	responseMap.get("comment");
			this.createdDt			= (LocalDateTime) 	responseMap.get("createdDt") 		== null ? null : (LocalDateTime) 	responseMap.get("createdDt");
			this.createdNo			= (Integer) 	responseMap.get("createdNo")			== null ? null : (Integer) 	responseMap.get("createdNo");
			this.updatedDt			= (LocalDateTime) 	responseMap.get("updatedDt") 		== null ? null : (LocalDateTime) 	responseMap.get("updatedDt");
			this.updatedNo			= (Integer) 	responseMap.get("updatedNo")			== null ? null : (Integer) 	responseMap.get("updatedNo");
		}
	}
	
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewCommentsRes {
        private List<CommentDto.ViewCommentRes> comments;
        private Integer totalCount;

        public ViewCommentsRes(List<CommentDto.ViewCommentRes> comments, Integer totalCount) {
            this.comments 	= comments;
            this.totalCount = totalCount;
        }
    }
    
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewCommentReq {
		private Integer postFk;
		private Integer memberFk;
		private Integer parents;
		private Integer deps;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	} 
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AddCommentReq {
		@NotNull(message = "게시글 고유번호가 비어있습니다.")
		private Integer postFk;
		@NotNull(message = "유저 고유번호가 비어있습니다.")
		private Integer memberSeq;
		private Integer parents;
		private Integer deps;
		@NotEmpty(message = "댓글이 비어있습니다.")
		private String comment;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}   
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SetCommentReq {
		@NotNull(message = "댓글 고유번호가 비어있습니다.")
		private Integer commentSeq;
		@NotNull(message = "유저 고유번호가 비어있습니다.")
		private Integer memberSeq;
		@NotEmpty(message = "댓글이 비어있습니다.")
		private String comment;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}   
}
