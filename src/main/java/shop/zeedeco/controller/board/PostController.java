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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import shop.zeedeco.dto.board.PostDto;
import shop.zeedeco.response.ApiResult;
import shop.zeedeco.service.board.PostService;
import shop.zeedeco.util.MapUtil;


@Validated
@RestController
@RequestMapping("/post")
@Tag(name = "post-controller", description = "게시글")
public class PostController {
	
	@Autowired
	private PostService postService;
	
    @PostMapping("/r")
    @Operation(summary = "게시글 목록")
    public ApiResult getPosts (
        @RequestBody @Valid final PostDto req
    ) {
        return ApiResult.successBuilder(postService.getPosts(MapUtil.toMap(req), true));
    }
    
    @GetMapping("/r/{postSeq}")
    @Operation(summary = "게시글 조회")
    public ApiResult getPost ( 
        @PathVariable @Positive(message = "postSeq는 양수여야 합니다.") @Parameter(description = "게시글 고유번호", example = "1") Integer postSeq
    ) {
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("postSeq", postSeq);
        return ApiResult.successBuilder(postService.getPosts(requestMap, false));
    }
    
    @PostMapping("/a")
    @Operation(summary = "게시글 등록")
    public ApiResult addPost (
        @RequestBody @Valid final PostDto req
    ) {
        return ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), true, false, "regist"));
    }
    
    @PostMapping("/m")
    @Operation(summary = "게시글 수정")
    public ApiResult setPost (
        @RequestBody @Valid final PostDto req
    ) {        
        return ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), false, false, "modify"));
    }
    
    @PostMapping("/d-l")
    @Operation(summary = "게시글 물리적 삭제")
    public ApiResult logicalRemovePost (
        @RequestBody @Valid final PostDto req    
    ) {
        return ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), false, true, "remove"));
    }   
    
    @PostMapping("/d-p")
    @Operation(summary = "게시글 논리적 삭제")
    public ApiResult physicalRemovePost (
        @RequestBody @Valid final PostDto req
    ) {
        return ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), false, false, "remove"));
    }
}
