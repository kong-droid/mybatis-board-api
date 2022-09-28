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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.zeedeco.dto.memo.MemoDto;
import shop.zeedeco.dto.memo.MemoDto.HandleMemoRes;
import shop.zeedeco.service.MemoService;
import shop.zeedeco.util.MapUtil;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {
	
	private final MemoService memoService;
	
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/r")
	public MemoDto.ViewMemosRes getMemos (
		@RequestBody @Valid final MemoDto.ViewMemoReq req
	) throws Exception {
		Map<String, Object> responseMap = memoService.getMemos(MapUtil.toMap(req));
		List<Map<String, Object>> memos = (List<Map<String, Object>>) responseMap.get("memos");
		return new MemoDto.ViewMemosRes(memos.stream().map(MemoDto.ViewMemoRes::new).collect(Collectors.toList()));
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/r/{memoSeq}")
	public MemoDto.ViewMemoRes getMemo (@PathVariable @Positive(message = "memoSeq는 양수여야 합니다.") Integer memoSeq) {
		return new MemoDto.ViewMemoRes(memoService.getMemo(memoSeq));
	} 
	
	@ResponseStatus(value = HttpStatus.CREATED, reason = "메모를 저장했습니다.")
	@PostMapping("/a")
	public HandleMemoRes addMemo (
		@RequestBody @Valid final MemoDto.HandleMemoReq req
	) {
		return new MemoDto.HandleMemoRes(memoService.handleMemo(MapUtil.toMap(req), null));
	} 

	@ResponseStatus(value = HttpStatus.OK, reason = "메모를 수정했습니다.")
	@PostMapping("/s/{memoSeq}")
	public HandleMemoRes setMemo (
		@PathVariable @Positive(message = "memoSeq는 양수여야 합니다. ") @Valid Integer memoSeq
		, @RequestBody @Valid final MemoDto.HandleMemoReq req
	) {
		return new MemoDto.HandleMemoRes(memoService.handleMemo(MapUtil.toMap(req), memoSeq));
	} 
	
	@ResponseStatus(value = HttpStatus.OK, reason = "메모를 삭제했습니다.")
	@PostMapping("/d/{memoSeq}")
	public void physicalRemoveMemo(@PathVariable @Positive(message = "memoSeq는 양수여야 합니다.") @Valid Integer memoSeq) {
		this.memoService.physicalRemoveMemo(memoSeq);
	} 
}
