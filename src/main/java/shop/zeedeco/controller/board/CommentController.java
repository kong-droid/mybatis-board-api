package shop.zeedeco.controller.board;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import shop.zeedeco.dto.board.CommentDto;
import shop.zeedeco.response.ApiResult;
import shop.zeedeco.service.board.CommentService;
import shop.zeedeco.util.MapUtil;


@Validated
@RestController
@RequestMapping("/comment")
@Tag(name = "comment-controller", description = "´ñ±Û")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
    @PostMapping("/a")
    @Operation(summary = "´ñ±Û µî·Ï")
    public ApiResult addComment (
        @RequestBody @Valid final CommentDto req
    ) {
        return ApiResult.successBuilder(commentService.handleComment(MapUtil.toMap(req), true, "regist"));
    }
    
    @PostMapping("/m")
    @Operation(summary = "´ñ±Û ¼öÁ¤")
    public ApiResult setComment (
        @RequestBody @Valid final CommentDto req
    ) {        
        return ApiResult.successBuilder(commentService.handleComment(MapUtil.toMap(req), false, "modify"));
    }
    
    @PostMapping("/d")
    @Operation(summary = "´ñ±Û »èÁ¦")
    public ApiResult removeComment (
        @RequestBody @Valid final CommentDto req    
    ) {
        return ApiResult.successBuilder(commentService.handleComment(MapUtil.toMap(req), false, "remove"));
    }   
    
}
