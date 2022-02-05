package shop.zeedeco.controller.board;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
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
import shop.zeedeco.service.board.BoardService;

@Validated
@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/list")
	public BoardDto.ViewBoardsRes getBoards(
			@RequestBody @Valid final BoardDto.ViewBoardReq req,
			@RequestParam @PositiveOrZero Integer page,
			@RequestParam @PositiveOrZero Integer size
	) throws Exception {
		Map<String, Object> responseMap = boardService.getBoards(req.toMap(), page, size);
		List<Map<String, Object>> boards = (List<Map<String, Object>>) responseMap.get("boards");
		Integer totalCount = (Integer) responseMap.get("totalCount");
		return new BoardDto.ViewBoardsRes(boards.stream().map(BoardDto.ViewBoardRes::new).collect(Collectors.toList()), totalCount);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{boardSeq}")
	public BoardDto.ViewBoardRes getBoard(@PathVariable @Valid int boardSeq) throws Exception {
		Map<String, Object> responseMap = boardService.getBoard(boardSeq);
		return new BoardDto.ViewBoardRes(responseMap);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void addBoard(@RequestBody @Valid final BoardDto.AddBoardReq req) throws Exception {
		this.boardService.addBoard(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public void setBoard(@RequestBody @Valid final BoardDto.SetBoardReq req) throws Exception {
		this.boardService.setBoard(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping
	public void logicalRemoveBoard(@RequestBody @Valid final BoardDto.SetBoardByDelYnReq req) throws Exception {
		this.boardService.logicalRemoveBoard(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{boardSeq}")
	public void physicalRemoveBoard(@PathVariable @Valid int boardSeq) throws Exception {
		this.boardService.physicalRemoveBoard(boardSeq);
	}
}
