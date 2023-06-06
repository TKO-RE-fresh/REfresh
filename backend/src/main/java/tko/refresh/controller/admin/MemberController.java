package tko.refresh.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.admin.MemberDetailDto;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.dto.admin.MemberSearchDto;
import tko.refresh.dto.admin.MemberUpdateDto;
import tko.refresh.repository.department.MemberDepartmentRepository;
import tko.refresh.service.admin.MemberService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/member")
public class MemberController {

    private final MemberService memberService;

    private final MemberDepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity getMemberAllList(@RequestParam(required = false) MemberSearchDto searchDto,
                                           @RequestParam int page) {
        int formatPage = page;

        Page<MemberDto> list;
        if(searchDto == null) {
            System.out.println("전체 사원 조회");
            list = memberService.getAllMemberList(formatPage);
        } else {
            System.out.println("검색 사원 조회");
            list = memberService.getSearchMemberList(searchDto, formatPage);
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetailDto> detailMember(@PathVariable String memberId) {

        MemberDetailDto memberDetailDto = memberService.getMemberDetail(memberId);

        return ResponseEntity.ok().body(memberDetailDto);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<String> editMember(@PathVariable String memberId, @RequestBody MemberUpdateDto memberUpdateDto) {

        memberService.editMember(memberId, memberUpdateDto);

        return ResponseEntity.ok("Success");
    }

    @GetMapping("/new")
    public ResponseEntity getNewMemberForm() {

        List<String> list = departmentRepository.getDepartmentNameList();

        return ResponseEntity.ok().body(list);
    }

}
