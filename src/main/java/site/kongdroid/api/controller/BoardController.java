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
import site.kongdroid.api.dto.request.board.BoardDto;
import site.kongdroid.api.dto.response.ApiResult;
import site.kongdroid.api.service.BoardService;
import site.kongdroid.api.util.MapUtil;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Tag(name = "게시판", description = "게시판 관리 API")
public class BoardController {
		
	private final BoardService boardService;
	
	@PostMapping("/read")
	@Operation(summary = "게시판 목록")
    public Callable<ApiResult> list(@RequestBody @Valid final BoardDto req){
		return () -> ApiResult.successBuilder(boardService.getBoards(MapUtil.toMap(req), true));
	}
	
	@GetMapping("/read/{boardSeq}")
	@Operation(summary = "게시판 조회")
    public Callable<ApiResult> get(@PathVariable @Positive(message = "boardSeq는 양수여야 합니다.") @Parameter(description = "게시판 고유번호", example = "1") Integer boardSeq){
	    val requestMap = new HashMap<String, Object>();
	    requestMap.put("boardSeq", boardSeq);
	    return () -> ApiResult.successBuilder(boardService.getBoards(requestMap, false));
	}

	@PostMapping("/register")
	@Operation(summary = "게시판 등록")
	public Callable<ApiResult> create(@RequestBody @Valid final BoardDto req){
	    return () -> ApiResult.successBuilder(boardService.handleBoard(MapUtil.toMap(req), true, false, "regist"));
	}
	
	@PostMapping("/modify")
	@Operation(summary = "게시판 수정")
    public Callable<ApiResult> update(@RequestBody @Valid final BoardDto req){
	    return () -> ApiResult.successBuilder(boardService.handleBoard(MapUtil.toMap(req), false, false, "modify"));
	}
	
	@PostMapping("/logical-delete")
	@Operation(summary = "게시판 논리적 삭제")
    public Callable<ApiResult> logicalDelete(@RequestBody @Valid final BoardDto req){
	    return () -> ApiResult.successBuilder(boardService.handleBoard(MapUtil.toMap(req), false, true, "remove"));
	}	
	
	@PostMapping("/physical-delete")
	@Operation(summary = "게시판 물리적 삭제")
    public Callable<ApiResult> physicalDelete(@RequestBody @Valid final BoardDto req){
	    return () -> ApiResult.successBuilder(boardService.handleBoard(MapUtil.toMap(req), false, false, "remove"));
	}
}
