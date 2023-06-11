package tko.refresh.controller.admin;

<<<<<<< HEAD
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
=======
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.service.admin.MemberService;

import java.util.List;
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/member")
public class MemberController {

    private final MemberService memberService;

<<<<<<< HEAD
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


=======
    @GetMapping
    public ResponseEntity getMemberAllList() {
        List<MemberDto> list = memberService.getAllMemberList(0);

        return ResponseEntity.ok().body(list);
    }
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
}
