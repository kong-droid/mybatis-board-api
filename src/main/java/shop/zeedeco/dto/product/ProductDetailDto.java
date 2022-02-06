package shop.zeedeco.dto.product;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductDetailDto {
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class ViewProductDetailRes {
		private Integer detailSeq;
		private Integer productFk;
		private LocalDateTime startDt;
		private LocalDateTime endDt;
		private Integer price;
		private Integer discountPrice;
		private Integer discountRate;
		private String discountYn;
		private String useYn;
		private String delYn;
		private LocalDateTime createdDt;
		private Integer createdNo;
		private LocalDateTime updatedDt;
		private Integer updatedNo;
		
		public ViewProductDetailRes(Map<String, Object> responseMap) {
			this.detailSeq			= (Integer) 	responseMap.get("detailSeq") 		== null ? null : (Integer) 	responseMap.get("detailSeq");
			this.productFk			= (Integer) 	responseMap.get("productFk") 		== null ? null : (Integer) 	responseMap.get("productFk");
			this.startDt			= (LocalDateTime) 		responseMap.get("startDt") 	== null ? null : (LocalDateTime) 	responseMap.get("startDt");
			this.endDt				= (LocalDateTime) 		responseMap.get("endDt")	== null ? null : (LocalDateTime) 	responseMap.get("endDt");
			this.price				= (Integer) 	responseMap.get("price")			== null ? null : (Integer) 	responseMap.get("price");
			this.discountPrice		= (Integer) 	responseMap.get("discountPrice")	== null ? null : (Integer) 	responseMap.get("discountPrice");
			this.discountRate		= (Integer) 	responseMap.get("discountRate")		== null ? null : (Integer) 	responseMap.get("discountRate");
			this.discountYn			= (String) 		responseMap.get("discountYn")		== null ? null : (String) 	responseMap.get("discountYn");
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
	public static class AddProductDetail {
		@NotNull(message = "상품 고유번호가 없습니다.")
		private Integer productSeq;
		@NotEmpty(message = "시작일 없습니다.")
		private String startDt;
		@NotEmpty(message = "종료일 없습니다.")
		private String endDt;
		@NotNull(message = "상품 원가가 없습니다.")
		private Integer price;
		@NotNull(message = "상품 할인가가 없습니다.")
		private Integer discountPrice;
		@NotNull(message = "상품 할인율이 없습니다.")
		private Integer discountRate;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String discountYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String useYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String delYn;
		@NotNull(message = "유저 고유번호가 없습니다.")
		private Integer memberSeq;
		
		public Map<String, Object> toMap() throws Exception {
			return new ObjectMapper().convertValue(this, Map.class);
		}
	}
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SetProductDetail {
		@NotNull(message = "상품 상세 고유번호가 없습니다.")
		private Integer productDetailSeq;
		@NotEmpty(message = "시작일 없습니다.")
		private String startDt;
		@NotEmpty(message = "종료일 없습니다.")
		private String endDt;
		@NotNull(message = "상품 원가가 없습니다.")
		private Integer price;
		@NotNull(message = "상품 할인가가 없습니다.")
		private Integer discountPrice;
		@NotNull(message = "상품 할인율이 없습니다.")
		private Integer discountRate;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String discountYn;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String useYn;
		@NotNull(message = "유저 고유번호가 없습니다.")
		private Integer memberSeq;
		
		public Map<String, Object> toMap() throws Exception {
			return new ObjectMapper().convertValue(this, Map.class);
		}
	}
	
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SetProductDetailByDelYn {
		@NotNull(message = "상품 상세 고유번호가 없습니다.")
		private Integer productDetailSeq;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String delYn;
		@NotNull(message = "유저 고유번호가 없습니다.")
		private Integer memberSeq;
		
		public Map<String, Object> toMap() throws Exception {
			return new ObjectMapper().convertValue(this, Map.class);
		}
	}
	
}
