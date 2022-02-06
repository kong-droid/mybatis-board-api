package shop.zeedeco.dto.product;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReservationDto {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class AddReservation {
		@NotNull(message = "상품 상세 고유번호가 없습니다.")
		private Integer productDetailSeq;
		@NotEmpty(message = "예약 정보가 없습니다.")
		private String comment;
		private String remark;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String cancelYn;
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
	public static class SetReservation {
		@NotNull(message = "예약 고유번호가 없습니다.")
		private Integer reservationSeq;
		@NotEmpty(message = "예약 정보가 없습니다.")
		private String comment;
		private String remark;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String cancelYn;
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
	public static class SetReservationByDelYn {
		@NotNull(message = "예약 고유번호가 없습니다.")
		private Integer reservationSeq;
		@Pattern(regexp = "^(Y|N)$", message = "'Y' 또는 'N' 이 입력되어야 합니다.")
		private String delYn;
		@NotNull(message = "유저 고유번호가 없습니다.")
		private Integer memberSeq;
		
		public Map<String, Object> toMap() throws Exception {
			return new ObjectMapper().convertValue(this, Map.class);
		}
	}
}
