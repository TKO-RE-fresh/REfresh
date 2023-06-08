package tko.refresh.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.admin.*;
import tko.refresh.dto.member.MemberJoinDto;
import tko.refresh.repository.department.MemberDepartmentRepository;
import tko.refresh.service.admin.MemberService;
import tko.refresh.service.login.LoginService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/member")
public class MemberController {

    private final MemberService memberService;

    private final MemberDepartmentRepository departmentRepository;

    private final LoginService loginService;

    @GetMapping
    public ResponseEntity getMemberAllList(@ModelAttribute MemberSearchDto searchDto,
                                           @RequestParam Optional<Integer> page) {
        int formatPage = page.orElse(1);

        Page<MemberDto> list;

        list = memberService.getSearchMemberList(searchDto, formatPage);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetailDto> detailMember(@PathVariable String memberId) {
        System.out.println(memberId);
        MemberDetailDto memberDetailDto = memberService.getMemberDetail(memberId);

        return ResponseEntity.ok().body(memberDetailDto);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<String> editMember(@PathVariable String memberId, @RequestBody @Valid MemberUpdateDto memberUpdateDto) {

        memberService.editMember(memberId, memberUpdateDto);

        return ResponseEntity.ok("Success");
    }

    @GetMapping("/new")
    public ResponseEntity getNewMemberForm() {

        List<String> list = departmentRepository.getDepartmentNameList();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/searchForm")
    public ResponseEntity getSearchFrom() {

        SearchFormDto searchFormDto = memberService.getSearchForm();

        return ResponseEntity.ok().body(searchFormDto);
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody @Valid MemberJoinDto memberJoinDto) {

        loginService.signup(memberJoinDto);

        return ResponseEntity.ok("Success");
    }


}
