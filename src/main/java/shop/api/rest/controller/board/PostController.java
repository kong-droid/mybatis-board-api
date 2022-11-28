package shop.api.rest.controller.board;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
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
import shop.api.rest.dto.board.PostDto;
import shop.api.rest.response.ApiResult;
import shop.api.rest.service.board.PostService;
import shop.api.rest.util.MapUtil;


@Validated
@RestController
@RequestMapping("/post")
@Tag(name = "post-controller", description = "�Խñ�")
public class PostController {
	
	@Autowired
	private PostService postService;
	
    @PostMapping("/r")
    @Operation(summary = "�Խñ� ���")
    public ApiResult getPosts (
        @RequestBody @Valid final PostDto req
    ) {
        return ApiResult.successBuilder(postService.getPosts(MapUtil.toMap(req), true));
    }
    
    @GetMapping("/r/{postSeq}")
    @Operation(summary = "�Խñ� ��ȸ")
    public ApiResult getPost ( 
        @PathVariable @Positive(message = "postSeq�� ������� �մϴ�.") @Parameter(description = "�Խñ� ������ȣ", example = "1") Integer postSeq
    ) {
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("postSeq", postSeq);
        return ApiResult.successBuilder(postService.getPosts(requestMap, false));
    }
    
    @PostMapping("/a")
    @Operation(summary = "�Խñ� ���")
    public ApiResult addPost (
        @RequestBody @Valid final PostDto req
    ) {
        return ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), true, false, "regist"));
    }
    
    @PostMapping("/m")
    @Operation(summary = "�Խñ� ����")
    public ApiResult setPost (
        @RequestBody @Valid final PostDto req
    ) {        
        return ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), false, false, "modify"));
    }
    
    @PostMapping("/d-l")
    @Operation(summary = "�Խñ� ���� ����")
    public ApiResult logicalRemovePost (
        @RequestBody @Valid final PostDto req    
    ) {
        return ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), false, true, "remove"));
    }   
    
    @PostMapping("/d-p")
    @Operation(summary = "�Խñ� ������ ����")
    public ApiResult physicalRemovePost (
        @RequestBody @Valid final PostDto req
    ) {
        return ApiResult.successBuilder(postService.handlePost(MapUtil.toMap(req), false, false, "remove"));
    }
}
