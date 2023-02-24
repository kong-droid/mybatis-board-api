package site.kongdroid.api.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.auth.AuthDto;
import site.kongdroid.api.response.ApiResult;
import site.kongdroid.api.service.AuthService;
import site.kongdroid.api.util.MapUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "auth-controller", description = "인증")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
    @PostMapping("/authentication")
    @Operation(summary = "로그인")
    public ApiResult authentication (
        @RequestBody @Valid final AuthDto req
    ) {
        return ApiResult.successBuilder(authService.getAuth(MapUtil.toMap(req)));
    } 

    @PostMapping("/duplicate-check")
    @Operation(summary = "아이디 중복체크")
    public ApiResult getMemberByDuplicateCheck (
        @RequestBody @Valid final AuthDto req
    ) {
        return ApiResult.successBuilder(authService.getDuplicate(MapUtil.toMap(req)));
    } 

    @PostMapping("/change-password")    
    @Operation(summary = "비밀번호 수정")
    @ApiResponse(code = 200, message = "valid")
    public void setPassword (
        @RequestBody @Valid final AuthDto req        
    ) {
       authService.setPassword(MapUtil.toMap(req));
    } 
}
