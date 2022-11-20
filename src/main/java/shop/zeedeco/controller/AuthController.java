package shop.zeedeco.controller;


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
import shop.zeedeco.dto.auth.AuthDto;
import shop.zeedeco.dto.memo.MemoDto;
import shop.zeedeco.response.ApiResult;
import shop.zeedeco.service.AuthService;
import shop.zeedeco.util.MapUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "auth-controller", description = "����")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
    @PostMapping("/authentication")
    @Operation(summary = "�α���")
    public ApiResult authentication (
        @RequestBody @Valid final AuthDto req
    ) {
        return ApiResult.successBuilder(authService.getAuth(MapUtil.toMap(req)));
    } 

    @PostMapping("/duplicate-check")
    @Operation(summary = "���̵� �ߺ�üũ")
    public ApiResult getMemberByDuplicateCheck (
        @RequestBody @Valid final AuthDto req
    ) {
        return ApiResult.successBuilder(authService.getDuplicate(MapUtil.toMap(req)));
    } 

    @PostMapping("/change-password")    
    @Operation(summary = "��й�ȣ ����")
    @ApiResponse(code = 200, message = "valid")
    public void setPassword (
        @RequestBody @Valid final AuthDto req        
    ) {
       authService.setPassword(MapUtil.toMap(req));
    } 
}
