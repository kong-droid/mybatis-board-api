package site.kongdroid.api.controller;

import java.util.HashMap;
import java.util.concurrent.Callable;
import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.security.core.Authentication;
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
import lombok.val;
import site.kongdroid.api.dto.request.member.MemberDto;
import site.kongdroid.api.dto.response.ApiResult;
import site.kongdroid.api.service.MemberService;
import site.kongdroid.api.util.AuthUtil;
import site.kongdroid.api.util.MapUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "회원", description = "회원 관리 API")
public class MemberController {
	
	private final MemberService memberService;
	
    @PostMapping("/read")
    @Operation(summary = "회원 목록")
    public Callable<ApiResult> list(@RequestBody @Valid final MemberDto req) {
        return () -> ApiResult.successBuilder(memberService.getMembers(MapUtil.toMap(req), true));
    }
    
    @GetMapping("/read/{memberSeq}")
    @Operation(summary = "회원 조회")
    public Callable<ApiResult> get(@PathVariable
                                   @Positive(message = "memberSeq는 양수여야 합니다.")
                                   @Parameter(description = "회원 고유번호", example = "1") Integer memberSeq) {
        val requestMap = new HashMap<String, Object>();
        requestMap.put("memberSeq", memberSeq);
        return () -> ApiResult.successBuilder(memberService.getMembers(requestMap, false));
    }
    
    @PostMapping("/register")
    @Operation(summary = "회원 등록")
    public Callable<ApiResult> create(@RequestBody@Valid final MemberDto req)
            throws InternalResourceException {
        return () -> ApiResult.successBuilder(memberService.handleMember(null,
                MapUtil.toMap(req), true, "regist"));
    }
    
    @PostMapping("/modify")
    @Operation(summary = "회원 수정")
    public Callable<ApiResult> update(Authentication authentication,
                                      @RequestBody @Valid final MemberDto req)
            throws InternalResourceException, AuthException {
        Integer memberSeq = AuthUtil.memberSeq(authentication);
        return () -> ApiResult.successBuilder(memberService.handleMember(memberSeq,
                MapUtil.toMap(req), false, "modify"));
    }
    
    @PostMapping("/withdrawal")
    @Operation(summary = "회원 탈퇴")
    public Callable<ApiResult> logicalDelete(Authentication authentication)
            throws InternalResourceException, AuthException {
        Integer memberSeq = AuthUtil.memberSeq(authentication);
        return () -> ApiResult.successBuilder(memberService.handleMember(memberSeq ,
                null, false, "remove"));
    }

}
