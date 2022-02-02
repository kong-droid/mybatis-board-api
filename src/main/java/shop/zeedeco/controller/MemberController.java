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
import shop.zeedeco.service.MemberService;

@Slf4j
@Validated
@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/list")
	public MemberDto.ViewMembersRes getMembers(
			@RequestBody @Valid final MemberDto.ViewMemberReq req,
			@RequestParam @PositiveOrZero Integer page,
			@RequestParam @PositiveOrZero Integer size
	) throws Exception {
		Map<String, Object> responseMap = memberService.getMembers(req.toMap(), page, size);
		List<Map<String, Object>> members = (List<Map<String, Object>>) responseMap.get("members");
		Integer totalCount = (Integer) responseMap.get("totalCount");
		return new MemberDto.ViewMembersRes(members.stream().map(MemberDto.ViewMemberRes::new).collect(Collectors.toList()), totalCount);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{memberSeq}")
	public MemberDto.ViewMemberRes getMember(@PathVariable @Valid int memberSeq) throws Exception {
		Map<String, Object> responseMap = memberService.getMember(memberSeq);
		return new MemberDto.ViewMemberRes(responseMap);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void addMember(@RequestBody @Valid final MemberDto.AddMemberReq req) throws Exception {
		this.memberService.addMember(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	public void setMember(@RequestBody @Valid final MemberDto.SetMemberReq req) throws Exception {
		this.memberService.setMember(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping
	public void logicalRemoveMember(@RequestBody @Valid final MemberDto.LogicalRemoveMemberReq req) throws Exception {
		this.memberService.logicalRemoveMember(req.toMap());
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{memberSeq}")
	public void physicalRemoveMember(@PathVariable @Valid int memberSeq) throws Exception {
		this.memberService.physicalRemoveMember(memberSeq);
	}
}
