package shop.zeedeco.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.zeedeco.dto.attach.AttachDto;
import shop.zeedeco.service.AttachService;
import shop.zeedeco.util.MapUtil;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachController {
	
	private final AttachService attachService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/list")
	public AttachDto.ViewAttachesRes getAttaches(
			@RequestBody @Valid final AttachDto.ViewAttachReq req
	) throws Exception {
		Map<String, Object> responseMap = attachService.getAttaches(MapUtil.toMap(req));
		List<Map<String, Object>> attaches = (List<Map<String, Object>>) responseMap.get("attaches");
		return new AttachDto.ViewAttachesRes(attaches.stream().map(AttachDto.ViewAttachRes::new).collect(Collectors.toList()));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public AttachDto.ViewAddAttachesRes addAttach(@ModelAttribute @Valid AttachDto.AddAttachReq req) throws Exception {
		Map<String, Object> responseMap = attachService.addAttach(req);
		List<Map<String, Object>> attached = (List<Map<String, Object>>) responseMap.get("attached");
		return new AttachDto.ViewAddAttachesRes(attached.stream().map(AttachDto.ViewAddAttachRes::new).collect(Collectors.toList()));	
	} 
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{attachSeq}")
	public void physicalRemoveAttach(@Valid @PathVariable int attachSeq) throws Exception {
		this.attachService.physicalRemoveAttach(attachSeq);
	} 
}
