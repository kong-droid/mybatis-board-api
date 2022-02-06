package shop.zeedeco.controller.product;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.zeedeco.dto.product.ReservationDto;
import shop.zeedeco.service.product.ReservationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
	
	private final ReservationService reservationService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void addReservation(@RequestBody @Valid final ReservationDto.AddReservation req) throws Exception {
		this.reservationService.addReservation(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public void setReservation(@RequestBody @Valid final ReservationDto.SetReservation req) throws Exception {
		this.reservationService.setReservation(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping
	public void logicalRemoveReservation(@RequestBody @Valid final ReservationDto.SetReservationByDelYn req) throws Exception {
		this.reservationService.logicalRemoveReservation(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{reservationSeq}")
	public void physicalRemoveReservation(@PathVariable @Valid int reservationSeq) throws Exception {
		this.reservationService.physicalRemoveReservation(reservationSeq);
	}
}
