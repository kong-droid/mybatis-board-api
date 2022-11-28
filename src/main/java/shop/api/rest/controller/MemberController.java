package shop.api.rest.controller;

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
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.api.rest.dto.board.BoardDto;
import shop.api.rest.dto.member.MemberDto;
import shop.api.rest.response.ApiResult;
import shop.api.rest.service.MemberService;
import shop.api.rest.service.MemoService;
import shop.api.rest.util.MapUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "member-controller", description = "ȸ��")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
    @PostMapping("/r")
    @Operation(summary = "ȸ�� ���")
    public ApiResult getMembers (@RequestBody @Valid final MemberDto req) {
        return ApiResult.successBuilder(memberService.getMembers(MapUtil.toMap(req), true));
    }
    
    @GetMapping("/r/{memberSeq}")
    @Operation(summary = "ȸ�� ��ȸ")
    public ApiResult getBoard (@PathVariable @Positive(message = "memberSeq�� ������� �մϴ�.") @Parameter(description = "ȸ�� ������ȣ", example = "1") Integer memberSeq) {
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("memberSeq", memberSeq);
        return ApiResult.successBuilder(memberService.getMembers(requestMap, false));
    }
    
    @PostMapping("/a")
    @Operation(summary = "ȸ�� ���")
    public ApiResult addMember (@RequestBody @Valid final MemberDto req) throws InvalidKeyException, InternalResourceException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        return ApiResult.successBuilder(memberService.handleMember(MapUtil.toMap(req), true, "regist"));
    }
    
    @PostMapping("/m")
    @Operation(summary = "ȸ�� ����")
    public ApiResult setMember (@RequestBody @Valid final MemberDto req) throws InvalidKeyException, InternalResourceException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {        
        return ApiResult.successBuilder(memberService.handleMember(MapUtil.toMap(req), false, "modify"));
    }
    
    @PostMapping("/d-l")
    @Operation(summary = "ȸ�� ����")
    public ApiResult logicalRemoveMember(@RequestBody @Valid final MemberDto req) throws InvalidKeyException, InternalResourceException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        return ApiResult.successBuilder(memberService.handleMember(MapUtil.toMap(req), false, "remove"));
    }   
}
