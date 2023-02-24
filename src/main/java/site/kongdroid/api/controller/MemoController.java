package site.kongdroid.api.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
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
import site.kongdroid.api.dto.memo.MemoDto;
import site.kongdroid.api.response.ApiResult;
import site.kongdroid.api.service.MemoService;
import site.kongdroid.api.util.MapUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/memo")
@Tag(name = "memo-controller", description = "메모")
public class MemoController {
	
	private final MemoService memoService;
	
	@PostMapping("/r")
	@Operation(summary = "메모 조회")
	public ApiResult getMemos (
		@RequestBody @Valid final MemoDto req
	)  {
		return ApiResult.successBuilder(memoService.getMemos(MapUtil.toMap(req), true));
	}
	
	
	@GetMapping("/r/{memoSeq}")
	@Operation(summary = "메모 개별조회")
	public ApiResult getMemo (
		@PathVariable @Positive(message = "memoSeq는 양수여야 합니다.") @Parameter(description = "메모 고유번호", example = "1") Integer memoSeq
	) {
	    Map<String, Object> requestMap = new HashMap<String, Object>();
	    requestMap.put("memoSeq", memoSeq);
	    return ApiResult.successBuilder(memoService.getMemos(requestMap, false));
	} 
	
	
	@PostMapping("/a")
	@Operation(summary = "메모 등록")
	public ApiResult addMemo (
		@RequestBody @Valid final MemoDto req
	) {
		return ApiResult.successBuilder(memoService.handleMemo(MapUtil.toMap(req), true, "regist"));
	} 

    @PostMapping("/m")
    @Operation(summary = "메모 수정")
    public ApiResult setMemo (
        @RequestBody @Valid final MemoDto req
    ) {
        return ApiResult.successBuilder(memoService.handleMemo(MapUtil.toMap(req), false, "modify"));
    } 

	@PostMapping("/d/{memoSeq}")	
	@Operation(summary = "메모 삭제")
	public ApiResult physicalRemoveMemo (
        @PathVariable @Positive(message = "memoSeq는 양수여야 합니다.") @Parameter(description = "메모 고유번호", example = "1") Integer memoSeq        
	) {
       Map<String, Object> requestMap = new HashMap<String, Object>();
       requestMap.put("memoSeq", memoSeq);
       return ApiResult.successBuilder(memoService.handleMemo(requestMap, false, "remove"));
	} 
}
