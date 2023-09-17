package site.kongdroid.api.controller;


import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.val;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dto.request.auth.AuthDto;
import site.kongdroid.api.dto.response.ApiResult;
import site.kongdroid.api.service.AuthService;
import site.kongdroid.api.util.AuthUtil;
import site.kongdroid.api.util.MapUtil;
import java.util.concurrent.Callable;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증", description = "사용자 인증 관련 API")
public class AuthController {
	
	private final AuthService authService;
	
    @Operation(summary = "로그인")
    @PostMapping("/authentication")
    public Callable<ApiResult> authentication(HttpServletRequest request,
                                              @RequestBody @Valid final AuthDto req) {
        val userAgent = request.getHeader("User-Agent");
        return () -> ApiResult.successBuilder(authService.getAuth(userAgent, MapUtil.toMap(req)));
    } 

    @Operation(summary = "아이디 중복체크")
    @PostMapping("/duplicate-check")
    public Callable<ApiResult> duplicate(@RequestBody @Valid final AuthDto req){
        return () -> ApiResult.successBuilder(authService.getDuplicate(MapUtil.toMap(req)));
    } 

    @Operation(summary = "비밀번호 수정")
    @PostMapping("/change-password")
    public Callable<ApiResult> changePassword(Authentication authentication,
                               @RequestBody @Valid final AuthDto req) throws AuthException {
        Integer memberSeq = AuthUtil.memberSeq(authentication);
        return () -> ApiResult.successBuilder(authService.setPassword(memberSeq, MapUtil.toMap(req)));
    } 
}
