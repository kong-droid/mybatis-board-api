package site.kongdroid.api.controller;

import java.io.FileNotFoundException;
import java.util.concurrent.Callable;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dto.request.attach.AttachDto;
import site.kongdroid.api.dto.response.ApiResult;
import site.kongdroid.api.service.AttachServiceImpl;
import site.kongdroid.api.util.MapUtil;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/attach")
@Tag(name = "파일첨부", description = "파일첨부 관리 API")
public class AttachController {
	
	private final AttachServiceImpl attachService;
	
	@Operation(summary = "파일 목록 조회")
	@PostMapping("/read")
	public Callable<ApiResult> get(@RequestBody @Valid final AttachDto.ViewAttachReq req) {
		return () -> ApiResult.successBuilder(attachService.getAttaches(MapUtil.toMap(req)));
	}

	@Operation(summary = "파일 다운로드")
	@PostMapping("/download/{attachSeq}")
	public Callable<ResponseEntity<InputStreamResource>> download(@Positive(message = "attachSeq는 양수여야 함.") @PathVariable @Schema(example = "1", description = "占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙호") int attachSeq) throws FileNotFoundException {
		return () -> attachService.downloadFile(attachSeq);
	}

	@Operation(summary = "파일 저장")
	@PostMapping("/upload")
	public Callable<ApiResult> upload(@ModelAttribute @Valid AttachDto.AddAttachReq req) {
	    return () -> ApiResult.successBuilder(attachService.addAttach(req));
	} 
	
	@Operation(summary = "파일 삭제")
	@PostMapping("/delete/{attachSeq}")
	@ApiResponse(responseCode = "200", description = "delete file.")
	public Callable<ApiResult> delete(@Valid @PathVariable int attachSeq) {
	    return () -> ApiResult.successBuilder(attachService.physicalRemoveAttach(attachSeq));
	} 

}
