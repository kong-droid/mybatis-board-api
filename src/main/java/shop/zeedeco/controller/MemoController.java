package shop.zeedeco.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.zeedeco.dto.memo.MemoDto;
import shop.zeedeco.dto.memo.MemoDto.HandleMemoRes;
import shop.zeedeco.service.MemoService;
import shop.zeedeco.util.MapUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/memo")
@Tag(name = "memo-controller", description = "�޸� API")
public class MemoController {
	
	private final MemoService memoService;
	
	@PostMapping("/r")
	@Operation(summary = "�޸� ��ȸ")
	@ResponseStatus(value = HttpStatus.OK)
	public MemoDto.ViewMemosRes getMemos (
		@RequestBody @Valid final MemoDto.ViewMemoReq req
	) throws Exception {
		Map<String, Object> responseMap = memoService.getMemos(MapUtil.toMap(req));
		List<Map<String, Object>> memos = (List<Map<String, Object>>) responseMap.get("memos");
		return new MemoDto.ViewMemosRes(memos.stream().map(MemoDto.ViewMemoRes::new).collect(Collectors.toList()));
	}
	
	
	@GetMapping("/r/{memoSeq}")
	@Operation(summary = "�޸� ������ȸ")
	@ResponseStatus(value = HttpStatus.OK)
	public MemoDto.ViewMemoRes getMemo (
		@PathVariable @Positive(message = "memoSeq�� ������� �մϴ�.") @Parameter(description = "�޸� ������ȣ", example = "1") Integer memoSeq
	) {
		return new MemoDto.ViewMemoRes(memoService.getMemo(memoSeq));
	} 
	
	
	@PostMapping("/a")
	@Operation(summary = "�޸� ���")
	@ResponseStatus(value = HttpStatus.CREATED)
	public HandleMemoRes addMemo (
		@RequestBody @Valid final MemoDto.HandleMemoReq req
	) {
		return new MemoDto.HandleMemoRes(memoService.handleMemo(MapUtil.toMap(req), null));
	} 

	@PostMapping("/s/{memoSeq}")
	@Operation(summary = "�޸� ����")
	@ResponseStatus(value = HttpStatus.OK)
	public HandleMemoRes setMemo (
		@PathVariable @Positive(message = "memoSeq�� ������� �մϴ�. ") @Valid @Parameter(description = "�޸� ������ȣ", example = "1") Integer memoSeq
		, @RequestBody @Valid final MemoDto.HandleMemoReq req
	) {
		return new MemoDto.HandleMemoRes(memoService.handleMemo(MapUtil.toMap(req), memoSeq));
	} 
	@PostMapping("/d/{memoSeq}")	
	@Operation(summary = "�޸� ����")
	@ResponseStatus(value = HttpStatus.OK, reason = "�޸� �����߽��ϴ�.")
	public void physicalRemoveMemo (
		@PathVariable @Positive(message = "memoSeq�� ������� �մϴ�.") @Valid @Parameter(description = "�޸� ������ȣ", example = "1") Integer memoSeq) 
	{
		this.memoService.physicalRemoveMemo(memoSeq);
	} 
}
