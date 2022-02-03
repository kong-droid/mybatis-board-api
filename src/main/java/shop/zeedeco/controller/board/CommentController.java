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

import shop.zeedeco.dto.board.CommentDto;
import shop.zeedeco.dto.board.PostDto;
import shop.zeedeco.service.board.CommentService;

@Validated
@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/list")
	public CommentDto.ViewCommentsRes getComments(
			@RequestBody @Valid final CommentDto.ViewCommentReq req,
			@RequestParam @PositiveOrZero Integer page,
			@RequestParam @PositiveOrZero Integer size
	) throws Exception {
		Map<String, Object> responseMap = commentService.getComments(req.toMap(), page, size);
		List<Map<String, Object>> comments = (List<Map<String, Object>>) responseMap.get("comments");
		Integer totalCount = (Integer) responseMap.get("totalCount");
		return new CommentDto.ViewCommentsRes(comments.stream().map(CommentDto.ViewCommentRes::new).collect(Collectors.toList()), totalCount);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void addPost(@RequestBody @Valid final CommentDto.AddCommentReq req) throws Exception {
		this.commentService.addComment(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public void setPost(@RequestBody @Valid final CommentDto.SetCommentReq req) throws Exception {
		this.commentService.setComment(req.toMap());
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{commentSeq}")
	public void physicalRemovePost(@PathVariable @Valid int commentSeq) throws Exception {
		this.commentService.physicalRemoveComment(commentSeq);
	}
}
