package site.kongdroid.api.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dto.request.attach.AttachDto;
import site.kongdroid.api.dto.response.ApiResult;
import site.kongdroid.api.service.AttachService;
import site.kongdroid.api.util.MapUtil;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/attach")
@Tag(name = "파일첨부", description = "파일첨부 관리 API")
public class AttachController {
	
	private final AttachService attachService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/r")
	@Operation(summary = "파일 목록 조회")
	public ApiResult getAttaches (@RequestBody @Valid final AttachDto.ViewAttachReq req) {
		return ApiResult.successBuilder(attachService.getAttaches(MapUtil.toMap(req)));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/a")
	@Operation(summary = "파일 저장")
	public ApiResult addAttach(@ModelAttribute @Valid AttachDto.AddAttachReq req) throws IOException {
	    return ApiResult.successBuilder(attachService.addAttach(req));
	} 
	
	@ApiResponse(responseCode = "200", description = "delete file.")
	@PostMapping("/d-p/{attachSeq}")
	@Operation(summary = "파일 삭제")
	public ApiResult physicalRemoveAttach(@Valid @PathVariable int attachSeq) {
	    return ApiResult.successBuilder(attachService.physicalRemoveAttach(attachSeq));
	} 
	
	@ResponseStatus(HttpStatus.OK)
    @PostMapping("/r/{attachSeq}")
    @Operation(summary = "파일 다운로드")
    public ResponseEntity<InputStreamResource> downloadFile (@Positive(message = "attachSeq는 양수여야 함.") @PathVariable @Schema(example = "1", description = "占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙호") int attachSeq) throws FileNotFoundException {
        return attachService.downloadFile(attachSeq);
    }
	
}
