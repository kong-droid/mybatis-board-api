package site.kongdroid.api.controller;

import java.util.HashMap;
import java.util.Map;
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
import site.kongdroid.api.dto.request.board.PostDto;
import site.kongdroid.api.dto.response.ApiResult;
import site.kongdroid.api.service.PostService;
import site.kongdroid.api.util.MapUtil;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "게시글", description = "게시글 관리 API")
public class PostController {
	
	private final PostService postService;
	
    @PostMapping("/read")
    @Operation(summary = "게시글 목록")
    public Callable<ApiResult> list(@RequestBody @Valid final PostDto req){
        return () -> ApiResult.successBuilder(postService.getPosts(MapUtil.toMap(req), true));
    }
    
    @GetMapping("/read/{postSeq}")
    @Operation(summary = "게시글 조회")
    public Callable<ApiResult> get(@PathVariable @Positive(message = "postSeq는 양수여야 합니다.") @Parameter(description = "게시글 고유번호", example = "1") Integer postSeq){
        val requestMap = new HashMap<String, Object>();
        requestMap.put("postSeq", postSeq);
        return () -> ApiResult.successBuilder(postService.getPosts(requestMap, false));
    }
    
    @PostMapping("/register")
    @Operation(summary = "게시글 등록")
    public Callable<ApiResult> create(@RequestBody @Valid final PostDto req){
        return () -> ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), true, false, "regist"));
    }
    
    @PostMapping("/modify")
    @Operation(summary = "게시글 수정")
    public Callable<ApiResult> update(@RequestBody @Valid final PostDto req){
        return () -> ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), false, false, "modify"));
    }
    
    @PostMapping("/logical-delete")
    @Operation(summary = "게시글 논리적 삭제")
    public Callable<ApiResult> logicalDelete(@RequestBody @Valid final PostDto req){
        return () -> ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), false, true, "remove"));
    }   
    
    @PostMapping("/physical-delete")
    @Operation(summary = "게시글 물리적 삭제")
    public Callable<ApiResult> physicalDelete(@RequestBody @Valid final PostDto req){
        return () -> ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), false, false, "remove"));
    }
}
