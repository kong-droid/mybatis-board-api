package shop.api.rest.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.api.rest.dto.attach.AttachDto;
import shop.api.rest.response.ApiResult;
import shop.api.rest.service.AttachService;
import shop.api.rest.util.MapUtil;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/attach")
@Tag(name = "attach-controller", description = "����÷��")
public class AttachController {
	
	private final AttachService attachService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/r")
	@Operation(summary = "���� ���")
	public ApiResult getAttaches (@RequestBody @Valid final AttachDto.ViewAttachReq req) {
		return ApiResult.successBuilder(attachService.getAttaches(MapUtil.toMap(req)));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/a")
	@Operation(summary = "���� ���")
	public ApiResult addAttach(@ModelAttribute @Valid AttachDto.AddAttachReq req) throws IOException {
	    return ApiResult.successBuilder(attachService.addAttach(req));
	} 
	
	@ApiResponse(code = 200, message = "valid")
	@PostMapping("/d-p/{attachSeq}")
	@Operation(summary = "���� ����")
	public ApiResult physicalRemoveAttach(@Valid @PathVariable int attachSeq) {
	    return ApiResult.successBuilder(attachService.physicalRemoveAttach(attachSeq));
	} 
	
	@ResponseStatus(HttpStatus.OK)
    @PostMapping("/r/{attachSeq}")
    @Operation(summary = "���� �ٿ�ε�")
    public ResponseEntity<InputStreamResource> downloadFile (@Positive(message = "attachSeq�� ������� �մϴ�.") @PathVariable @Schema(example = "1", description = "���� ������ȣ") int attachSeq) throws FileNotFoundException {
        return attachService.downloadFile(attachSeq);
    }
	
}
