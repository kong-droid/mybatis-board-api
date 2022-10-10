package shop.zeedeco.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import shop.zeedeco.dto.member.MemberDto;
import shop.zeedeco.response.ApiResult;
import shop.zeedeco.service.MemberService;
import shop.zeedeco.util.MapUtil;

@Slf4j
@Validated
@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/list")
	public ApiResult getMembers(
			@RequestBody @Valid final MemberDto req,
			@RequestParam @PositiveOrZero Integer page,
			@RequestParam @PositiveOrZero Integer size
	) throws Exception {
		return ApiResult.successBuilder(memberService.getMembers(MapUtil.toMap(req), page, size));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{memberSeq}")
	public ApiResult getMember(@PathVariable @Valid int memberSeq) throws Exception {
		Map<String, Object> responseMap = memberService.getMember(memberSeq);
		return ApiResult.successBuilder(responseMap);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void addMember(@RequestBody @Valid final MemberDto req) throws Exception {
		this.memberService.addMember(MapUtil.toMap(req));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public void setMember(@RequestBody @Valid final MemberDto req) throws Exception {
		this.memberService.setMember(MapUtil.toMap(req));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping
	public void logicalRemoveMember(@RequestBody @Valid final MemberDto req) throws Exception {
		this.memberService.logicalRemoveMember(MapUtil.toMap(req));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{memberSeq}")
	public void physicalRemoveMember(@PathVariable @Valid int memberSeq) throws Exception {
		this.memberService.physicalRemoveMember(memberSeq);
	}
}
