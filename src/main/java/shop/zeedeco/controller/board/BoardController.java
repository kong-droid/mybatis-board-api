package shop.zeedeco.controller.board;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import shop.zeedeco.dto.board.BoardDto;
import shop.zeedeco.dto.board.BoardDto.HandleBoardRes;
import shop.zeedeco.service.board.BoardService;
import shop.zeedeco.util.MapUtil;

@Validated
@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/r")
	public BoardDto.ViewBoardsRes getBoards(
			@RequestBody @Valid final BoardDto.ViewBoardReq req,
			@RequestParam @PositiveOrZero Integer page,
			@RequestParam @PositiveOrZero Integer size
	) {
		Map<String, Object> responseMap = boardService.getBoards(MapUtil.toMap(req), page, size);
		List<Map<String, Object>> boards = (List<Map<String, Object>>) responseMap.get("boards");
		Integer totalCount = (Integer) responseMap.get("totalCount");
		return new BoardDto.ViewBoardsRes(boards.stream().map(BoardDto.ViewBoardRes::new).collect(Collectors.toList()), totalCount);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/r/{boardSeq}")
	public BoardDto.ViewBoardRes getBoard(@PathVariable @Valid int boardSeq) {
		Map<String, Object> responseMap = boardService.getBoard(boardSeq);
		return new BoardDto.ViewBoardRes(responseMap);
	}
	
	@ResponseStatus(value = HttpStatus.CREATED, reason = "저장되었습니다.")
	@PostMapping("/a")
	public BoardDto.HandleBoardRes addBoard(@RequestBody @Valid final BoardDto.HandleBoardReq req) {
		return new BoardDto.HandleBoardRes(boardService.handleBoard(MapUtil.toMap(req), null));
	}
	
	@ResponseStatus(value = HttpStatus.OK, reason = "수정되었습니다.")
	@PostMapping("/s")
	public BoardDto.HandleBoardRes setBoard(
		@PathVariable @Valid @Positive(message = "boardSeq는 양수여야합니다. ") Integer boardSeq
		, @RequestBody @Valid final BoardDto.HandleBoardReq req) {
		return new BoardDto.HandleBoardRes(boardService.handleBoard(MapUtil.toMap(req), boardSeq));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/d-l/{memberSeq}/{boardSeq}")
	public void logicalRemoveBoard (
		@PathVariable @Valid @Positive(message = "boardSeq는 양수여야합니다. ") Integer boardSeq
		, @PathVariable @Valid @Positive(message = "memberSeq는 양수여야합니다. ") Integer memberSeq
	) {
		boardService.handleRemoveBoard(boardSeq, memberSeq, false);
	}	
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/d-p/{memberSeq}/{boardSeq}")
	public void physicalRemoveBoard (
		@PathVariable @Valid @Positive(message = "boardSeq는 양수여야합니다. ") Integer boardSeq
		, @PathVariable @Valid @Positive(message = "memberSeq는 양수여야합니다. ") Integer memberSeq
	) {
		boardService.handleRemoveBoard(boardSeq, memberSeq, true);
	}
	
}
