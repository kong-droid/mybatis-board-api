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
import shop.zeedeco.dto.board.PostDto;
import shop.zeedeco.service.board.PostService;


@Validated
@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/list")
	public PostDto.ViewPostsRes getPosts(
			@RequestBody @Valid final PostDto.ViewPostReq req,
			@RequestParam @PositiveOrZero Integer page,
			@RequestParam @PositiveOrZero Integer size
	) throws Exception {
		Map<String, Object> responseMap = postService.getPosts(req.toMap(), page, size);
		List<Map<String, Object>> posts = (List<Map<String, Object>>) responseMap.get("posts");
		Integer totalCount = (Integer) responseMap.get("totalCount");
		return new PostDto.ViewPostsRes(posts.stream().map(PostDto.ViewPostRes::new).collect(Collectors.toList()), totalCount);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{postSeq}")
	public PostDto.ViewPostRes getPost(@PathVariable @Valid int postSeq) throws Exception {
		Map<String, Object> responseMap = postService.getPost(postSeq);
		return new PostDto.ViewPostRes(responseMap);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void addPost(@RequestBody @Valid final PostDto.AddPostReq req) throws Exception {
		this.postService.addPost(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public void setPost(@RequestBody @Valid final PostDto.SetPostReq req) throws Exception {
		this.postService.setPost(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping
	public void logicalRemovePost(@RequestBody @Valid final  PostDto.SetPostByDelYnReq req) throws Exception {
		this.postService.logicalRemovePost(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{postSeq}")
	public void physicalRemovePost(@PathVariable @Valid int postSeq) throws Exception {
		this.postService.physicalRemovePost(postSeq);
	}
}
