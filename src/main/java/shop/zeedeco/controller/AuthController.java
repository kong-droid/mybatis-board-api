package shop.zeedeco.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import shop.zeedeco.dto.auth.AuthDto;
import shop.zeedeco.dto.member.MemberDto;
import shop.zeedeco.response.ApiResult;
import shop.zeedeco.service.AuthService;

@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/login")
	public ApiResult getAuth(@RequestBody @Valid final AuthDto.ViewAuthReq req) throws Exception {
		Map<String, Object> responseMap = authService.getAuth(req.toMap());
		return ApiResult.successBuilder(responseMap);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/duplicate-check")
	public AuthDto.ViewAuthRes getDuplicate(@RequestParam @Valid String id) throws Exception {
		Map<String, Object> responseMap = authService.getDuplicate(id);
		return new AuthDto.ViewAuthRes(responseMap);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/password")
	public void setPassword(@RequestBody @Valid final AuthDto.SetAuthReq req) throws Exception {
		this.authService.setPassword(req.toMap());
	} 
}
