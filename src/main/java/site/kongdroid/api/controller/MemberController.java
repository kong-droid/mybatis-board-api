package site.kongdroid.api.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dto.member.MemberDto;
import site.kongdroid.api.response.ApiResult;
import site.kongdroid.api.service.MemberService;
import site.kongdroid.api.util.MapUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "member-controller", description = "회원")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
    @PostMapping("/r")
    @Operation(summary = "회원 목록")
    public ApiResult getMembers (@RequestBody @Valid final MemberDto req) {
        return ApiResult.successBuilder(memberService.getMembers(MapUtil.toMap(req), true));
    }
    
    @GetMapping("/r/{memberSeq}")
    @Operation(summary = "회원 조회")
    public ApiResult getBoard (@PathVariable @Positive(message = "memberSeq는 양수여야 합니다.") @Parameter(description = "회원 고유번호", example = "1") Integer memberSeq) {
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("memberSeq", memberSeq);
        return ApiResult.successBuilder(memberService.getMembers(requestMap, false));
    }
    
    @PostMapping("/a")
    @Operation(summary = "회원 등록")
    public ApiResult addMember (@RequestBody @Valid final MemberDto req) throws InvalidKeyException, InternalResourceException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        return ApiResult.successBuilder(memberService.handleMember(MapUtil.toMap(req), true, "regist"));
    }
    
    @PostMapping("/m")
    @Operation(summary = "회원 수정")
    public ApiResult setMember (@RequestBody @Valid final MemberDto req) throws InvalidKeyException, InternalResourceException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {        
        return ApiResult.successBuilder(memberService.handleMember(MapUtil.toMap(req), false, "modify"));
    }
    
    @PostMapping("/d-l")
    @Operation(summary = "회원 삭제")
    public ApiResult logicalRemoveMember(@RequestBody @Valid final MemberDto req) throws InvalidKeyException, InternalResourceException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        return ApiResult.successBuilder(memberService.handleMember(MapUtil.toMap(req), false, "remove"));
    }   
}
