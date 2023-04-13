package site.kongdroid.api.controller.board;


import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dto.board.CommentDto;
import site.kongdroid.api.response.ApiResult;
import site.kongdroid.api.service.board.CommentService;
import site.kongdroid.api.util.MapUtil;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Tag(name = "comment-controller", description = "댓글")
public class CommentController {
	
	
	private final CommentService commentService;
	
    @PostMapping("/a")
    @Operation(summary = "댓글 등록")
    public ApiResult addComment (
        @RequestBody @Valid final CommentDto req
    ) {
        return ApiResult.successBuilder(commentService.handleComment(MapUtil.toMap(req), true, "regist"));
    }
    
    @PostMapping("/m")
    @Operation(summary = "댓글 수정")
    public ApiResult setComment (
        @RequestBody @Valid final CommentDto req
    ) {        
        return ApiResult.successBuilder(commentService.handleComment(MapUtil.toMap(req), false, "modify"));
    }
    
    @PostMapping("/d")
    @Operation(summary = "댓글 삭제")
    public ApiResult removeComment (
        @RequestBody @Valid final CommentDto req    
    ) {
        return ApiResult.successBuilder(commentService.handleComment(MapUtil.toMap(req), false, "remove"));
    }   
    
}
