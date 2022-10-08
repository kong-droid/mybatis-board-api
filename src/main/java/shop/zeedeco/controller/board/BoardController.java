package shop.zeedeco.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import shop.zeedeco.dto.board.BoardDto;
import shop.zeedeco.response.ApiResult;
import shop.zeedeco.service.board.BoardService;
import shop.zeedeco.util.MapUtil;

@Validated
@RestController
@RequestMapping("/board")
@Tag(name = "board-controller", description = "게시판")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/r")
	@Operation(summary = "게시판 목록")
    public ApiResult getBoards (
		@RequestBody @Valid final BoardDto req
	) {
		return ApiResult.successBuilder(boardService.getBoards(MapUtil.toMap(req), true));
	}
	
	@GetMapping("/r/{boardSeq}")
	@Operation(summary = "게시판 조회")
    public ApiResult getBoard ( 
        @PathVariable @Positive(message = "boardSeq는 양수여야 합니다.") @Parameter(description = "게시판 고유번호", example = "1") Integer boardSeq
    ) {
	    Map<String, Object> requestMap = new HashMap<String, Object>();
	    requestMap.put("boardSeq", boardSeq);
	    return ApiResult.successBuilder(boardService.getBoards(requestMap, false));
	}
	
	@PostMapping("/a")
	@Operation(summary = "게시판 등록")
	public ApiResult addBoard (
        @RequestBody @Valid final BoardDto req
    ) {
	    return ApiResult.successBuilder(boardService.handleBoard(MapUtil.toMap(req), true, false, "regist"));
	}
	
	@PostMapping("/m")
	@Operation(summary = "게시판 수정")
    public ApiResult setBoard (
        @RequestBody @Valid final BoardDto req
    ) {        
	    return ApiResult.successBuilder(boardService.handleBoard(MapUtil.toMap(req), false, false, "modify"));
	}
	
	@PostMapping("/d-l")
	@Operation(summary = "게시판 물리적 삭제")
    public ApiResult logicalRemoveBoard (
        @RequestBody @Valid final BoardDto req    
    ) {
	    return ApiResult.successBuilder(boardService.handleBoard(MapUtil.toMap(req), false, true, "remove"));
	}	
	
	@PostMapping("/d-p")
	@Operation(summary = "게시판 논리적 삭제")
    public ApiResult physicalRemoveBoard (
        @RequestBody @Valid final BoardDto req
    ) {
	    return ApiResult.successBuilder(boardService.handleBoard(MapUtil.toMap(req), false, false, "remove"));
	}
	
}
