package site.kongdroid.api.controller;

import java.util.concurrent.Callable;
import javax.security.auth.message.AuthException;
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
import site.kongdroid.api.dto.request.memo.MemoDto;
import site.kongdroid.api.dto.response.ApiResult;
import site.kongdroid.api.service.MemoService;
import site.kongdroid.api.util.AuthUtil;
import site.kongdroid.api.util.MapUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/memo")
@Tag(name = "메모", description = "메모 관리 API")
public class MemoController {
	
	private final MemoService memoService;
	
	@PostMapping("/read")
	@Operation(summary = "메모 조회")
	public Callable<ApiResult> list(Authentication authentication,
									@RequestBody @Valid final MemoDto req) throws AuthException {
		Integer memberSeq = AuthUtil.memberSeq(authentication);
		return () -> ApiResult.successBuilder(memoService.getMemos(memberSeq, MapUtil.toMap(req)));
	}

	@PostMapping("/register")
	@Operation(summary = "메모 등록")
	public Callable<ApiResult> create(Authentication authentication,
									  @RequestBody@Valid final MemoDto req) throws AuthException {
		Integer memberSeq = AuthUtil.memberSeq(authentication);
		return () -> ApiResult.successBuilder(memoService.handleMemo(memberSeq, MapUtil.toMap(req),
				true, "regist"));
	} 

    @PostMapping("/modify")
    @Operation(summary = "메모 수정")
    public Callable<ApiResult> update(Authentication authentication,
									  @RequestBody @Valid final MemoDto req) throws AuthException {
		Integer memberSeq = AuthUtil.memberSeq(authentication);
        return () -> ApiResult.successBuilder(memoService.handleMemo(memberSeq, MapUtil.toMap(req),
				false, "modify"));
    } 

	@PostMapping("/delete")
	@Operation(summary = "메모 삭제")
	public Callable<ApiResult> physicalDelete(Authentication authentication,
											  @RequestBody @Valid final MemoDto req) throws AuthException {
		Integer memberSeq = AuthUtil.memberSeq(authentication);
		return () -> ApiResult.successBuilder(memoService.handleMemo(memberSeq, MapUtil.toMap(req), false, "remove"));
	} 
}
