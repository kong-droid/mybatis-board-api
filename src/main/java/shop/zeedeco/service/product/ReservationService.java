package shop.zeedeco.service.product;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.zeedeco.dao.CustomDao;
import shop.zeedeco.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class ReservationService {
	
	private final CustomDao dao;
	
	public void addReservation(Map<String, Object> requestMap) throws Exception {
		int effectRow = this.dao.dbInsert("reservation.addReservation", requestMap);
		if ( effectRow < 0 ) new BadRequestException("저장에 실패했습니다."); 
	}
	
	public void setReservation(Map<String, Object> requestMap) throws Exception {
		int effectRow = this.dao.dbUpdate("reservation.setReservation", requestMap);
		if ( effectRow < 0 ) new BadRequestException("저장에 실패했습니다."); 
	}
	
	public void logicalRemoveReservation(Map<String, Object> requestMap) throws Exception {
		int effectRow = this.dao.dbUpdate("reservation.setReservation", requestMap);
		if ( effectRow < 0 ) new BadRequestException("논리적 삭제에 실패했습니다."); 
	}
	
	public void physicalRemoveReservation(int reservationSeq) throws Exception {
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("reservationSeq", reservationSeq);
		int effectRow = this.dao.dbInsert("reservation.removeReservation", requestMap);
		if ( effectRow < 0 ) new BadRequestException("물리적 삭제에 실패했습니다."); 
	}
}
