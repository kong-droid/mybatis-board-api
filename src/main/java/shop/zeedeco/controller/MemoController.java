package shop.zeedeco.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import shop.zeedeco.service.MemoService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {
	
	private final MemoService memoService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/list")
	public MemoDto.ViewMemosRes getMemos(
			@RequestBody @Valid final MemoDto.ViewMemoReq req
	) throws Exception {
		Map<String, Object> responseMap = memoService.getMemos(req.toMap());
		List<Map<String, Object>> memos = (List<Map<String, Object>>) responseMap.get("memos");
		return new MemoDto.ViewMemosRes(memos.stream().map(MemoDto.ViewMemoRes::new).collect(Collectors.toList()));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping
	public void addMemo(@RequestBody @Valid final MemoDto.AddMemoReq req) throws Exception {
		this.memoService.addMemo(req.toMap());
	} 

	@ResponseStatus(HttpStatus.OK)
	@PatchMapping
	public void setMemo(@RequestBody @Valid final MemoDto.SetMemoReq req) throws Exception {
		this.memoService.setMemo(req.toMap());
	} 
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{memoSeq}")
	public void physicalRemoveMemo(@Valid @PathVariable int memoSeq) throws Exception {
		this.memoService.physicalRemoveMemo(memoSeq);
	} 
}
