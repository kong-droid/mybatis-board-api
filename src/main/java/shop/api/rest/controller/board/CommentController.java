package shop.api.rest.controller.board;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import shop.api.rest.dto.board.CommentDto;
import shop.api.rest.response.ApiResult;
import shop.api.rest.service.board.CommentService;
import shop.api.rest.util.MapUtil;


@Validated
@RestController
@RequestMapping("/comment")
@Tag(name = "comment-controller", description = "댓글")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
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
