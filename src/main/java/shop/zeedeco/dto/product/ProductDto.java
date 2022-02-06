package shop.zeedeco.dto.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewProductRes {
		private Integer productSeq;
		private Integer memberFk;
		private String category;
		private String title;
		private String content;
		private String thumbnail;
		private String remark;
		private String useYn;
		private String delYn;
		private LocalDateTime createdDt;
		private Integer createdNo;
		private LocalDateTime updatedDt;
		private Integer updatedNo;
		private List<ProductDetailDto.ViewProductDetailRes> details;
		
		public ViewProductRes(Map<String, Object> responseMap) {
			this.productSeq			= (Integer) 	responseMap.get("productSeq") 		== null ? null : (Integer) 	responseMap.get("productSeq");
			this.memberFk			= (Integer) 	responseMap.get("memberFk") 		== null ? null : (Integer) 	responseMap.get("memberFk");
			this.category			= (String) 		responseMap.get("category")  		== null ? null : (String) 	responseMap.get("category");
			this.title				= (String) 		responseMap.get("title")			== null ? null : (String) 	responseMap.get("title");
			this.content			= (String) 		responseMap.get("content")			== null ? null : (String) 	responseMap.get("content");
			this.thumbnail			= (String) 		responseMap.get("email")			== null ? null : (String) 	responseMap.get("email");
			this.remark				= (String) 		responseMap.get("remark")			== null ? null : (String) 	responseMap.get("remark");
			this.useYn				= (String) 		responseMap.get("useYn")			== null ? null : (String) 	responseMap.get("useYn");
			this.delYn				= (String) 		responseMap.get("delYn")			== null ? null : (String) 	responseMap.get("delYn");
			this.createdDt			= (LocalDateTime) 	responseMap.get("createdDt") 	== null ? null : (LocalDateTime) 	responseMap.get("createdDt");
			this.createdNo			= (Integer) 	responseMap.get("createdNo")		== null ? null : (Integer) 	responseMap.get("createdNo");
			this.updatedDt			= (LocalDateTime) 	responseMap.get("updatedDt") 	== null ? null : (LocalDateTime) 	responseMap.get("updatedDt");
			this.updatedNo			= (Integer) 	responseMap.get("updatedNo")		== null ? null : (Integer) 	responseMap.get("updatedNo");
			this.details			= (List<ProductDetailDto.ViewProductDetailRes>) responseMap.get("details") == null ? null : (List<ProductDetailDto.ViewProductDetailRes>) responseMap.get("details");
		}
	}
	
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ViewProductsRes {
        private List<ProductDto.ViewProductRes> products;
        private Integer totalCount;

        public ViewProductsRes(List<ProductDto.ViewProductRes> products, Integer totalCount) {
            this.products 	= products;
            this.totalCount = totalCount;
        }
    }

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewProductReq {
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
	public static class AddProductReq {
		@NotNull(message = "유저 고유번호가 없습니다.")
		private Integer memberSeq;
		@NotEmpty(message = "카테고리 값이 없습니다.")
		private String category;
		@NotEmpty(message = "상품명이 없습니다.")
		private String title;
		@NotEmpty(message = "상품 내용이 없습니다.")
		private String content;
		private String thumbnail;
		private String remark;
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
	public static class SetProductReq {
		@NotNull(message = "상품 고유번호가 없습니다.")
		private Integer productSeq;		
		@NotNull(message = "유저 고유번호가 없습니다.")
		private Integer memberSeq;
		@NotEmpty(message = "카테고리 값이 없습니다.")
		private String category;
		@NotEmpty(message = "상품명이 없습니다.")
		private String title;
		@NotEmpty(message = "상품 내용이 없습니다.")
		private String content;
		private String thumbnail;
		private String remark;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String useYn;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SetProductByDelYn {
		@NotNull(message = "상품 고유번호가 없습니다.")
		private Integer productSeq;
		@NotNull(message = "유저 고유번호가 없습니다.")
		private Integer memberSeq;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String delYn;
		
        public Map<String, Object> toMap() throws Exception {
            return new ObjectMapper().convertValue(this, Map.class);
        }
	}
		
}
