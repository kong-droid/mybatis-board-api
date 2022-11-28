package shop.api.rest.controller;

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
import shop.api.rest.dto.memo.MemoDto;
import shop.api.rest.response.ApiResult;
import shop.api.rest.service.MemoService;
import shop.api.rest.util.MapUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/memo")
@Tag(name = "memo-controller", description = "�޸�")
public class MemoController {
	
	private final MemoService memoService;
	
	@PostMapping("/r")
	@Operation(summary = "�޸� ��ȸ")
	public ApiResult getMemos (
		@RequestBody @Valid final MemoDto req
	)  {
		return ApiResult.successBuilder(memoService.getMemos(MapUtil.toMap(req), true));
	}
	
	
	@GetMapping("/r/{memoSeq}")
	@Operation(summary = "�޸� ������ȸ")
	public ApiResult getMemo (
		@PathVariable @Positive(message = "memoSeq�� ������� �մϴ�.") @Parameter(description = "�޸� ������ȣ", example = "1") Integer memoSeq
	) {
	    Map<String, Object> requestMap = new HashMap<String, Object>();
	    requestMap.put("memoSeq", memoSeq);
	    return ApiResult.successBuilder(memoService.getMemos(requestMap, false));
	} 
	
	
	@PostMapping("/a")
	@Operation(summary = "�޸� ���")
	public ApiResult addMemo (
		@RequestBody @Valid final MemoDto req
	) {
		return ApiResult.successBuilder(memoService.handleMemo(MapUtil.toMap(req), true, "regist"));
	} 

    @PostMapping("/m")
    @Operation(summary = "�޸� ����")
    public ApiResult setMemo (
        @RequestBody @Valid final MemoDto req
    ) {
        return ApiResult.successBuilder(memoService.handleMemo(MapUtil.toMap(req), false, "modify"));
    } 

	@PostMapping("/d/{memoSeq}")	
	@Operation(summary = "�޸� ����")
	public ApiResult physicalRemoveMemo (
        @PathVariable @Positive(message = "memoSeq�� ������� �մϴ�.") @Parameter(description = "�޸� ������ȣ", example = "1") Integer memoSeq        
	) {
       Map<String, Object> requestMap = new HashMap<String, Object>();
       requestMap.put("memoSeq", memoSeq);
       return ApiResult.successBuilder(memoService.handleMemo(requestMap, false, "remove"));
	} 
}
