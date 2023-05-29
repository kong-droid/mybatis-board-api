package site.kongdroid.api.controller;

import java.util.HashMap;
import java.util.concurrent.Callable;
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

import lombok.val;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dto.request.memo.MemoDto;
import site.kongdroid.api.dto.response.ApiResult;
import site.kongdroid.api.service.MemoService;
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
	public Callable<ApiResult> list(@RequestBody @Valid final MemoDto req) {
		return () -> ApiResult.successBuilder(memoService.getMemos(MapUtil.toMap(req), true));
	}
	
	
	@GetMapping("/read/{memoSeq}")
	@Operation(summary = "메모 개별조회")
	public Callable<ApiResult> get(@PathVariable @Positive(message = "memoSeq는 양수여야 합니다.") @Parameter(description = "메모 고유번호", example = "1") Integer memoSeq){
	    val requestMap = new HashMap<String, Object>();
	    requestMap.put("memoSeq", memoSeq);
	    return () -> ApiResult.successBuilder(memoService.getMemos(requestMap, false));
	} 
	
	
	@PostMapping("/register")
	@Operation(summary = "메모 등록")
	public Callable<ApiResult> create(@RequestBody @Valid final MemoDto req){
		return () -> ApiResult.successBuilder(memoService.handleMemo(MapUtil.toMap(req), true, "regist"));
	} 

    @PostMapping("/modify")
    @Operation(summary = "메모 수정")
    public Callable<ApiResult> update(@RequestBody @Valid final MemoDto req){
        return () -> ApiResult.successBuilder(memoService.handleMemo(MapUtil.toMap(req), false, "modify"));
    } 

	@PostMapping("/delete/{memoSeq}")
	@Operation(summary = "메모 삭제")
	public Callable<ApiResult> physicalDelete(@PathVariable @Positive(message = "memoSeq는 양수여야 합니다.") @Parameter(description = "메모 고유번호", example = "1") Integer memoSeq){
       val requestMap = new HashMap<String, Object>();
       requestMap.put("memoSeq", memoSeq);
       return () -> ApiResult.successBuilder(memoService.handleMemo(requestMap, false, "remove"));
	} 
}
