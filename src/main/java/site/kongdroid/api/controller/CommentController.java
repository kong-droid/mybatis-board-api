package site.kongdroid.api.controller;


import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dto.request.board.CommentDto;
import site.kongdroid.api.dto.response.ApiResult;
import site.kongdroid.api.service.CommentService;
import site.kongdroid.api.util.MapUtil;

import java.util.concurrent.Callable;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Tag(name = "댓글", description = "댓글 관리 API")
public class CommentController {

	private final CommentService commentService;
	
    @PostMapping("/register")
    @Operation(summary = "댓글 등록")
    public Callable<ApiResult> create(@RequestBody @Valid final CommentDto req){
        return () -> ApiResult.successBuilder(commentService.handleComment(MapUtil.toMap(req), true, "regist"));
    }
    
    @PostMapping("/modify")
    @Operation(summary = "댓글 수정")
    public Callable<ApiResult> update(@RequestBody @Valid final CommentDto req){
        return () -> ApiResult.successBuilder(commentService.handleComment(MapUtil.toMap(req), false, "modify"));
    }
    
    @PostMapping("/delete")
    @Operation(summary = "댓글 삭제")
    public Callable<ApiResult> removeComment(@RequestBody @Valid final CommentDto req){
        return () -> ApiResult.successBuilder(commentService.handleComment(MapUtil.toMap(req), false, "remove"));
    }   
    
}
