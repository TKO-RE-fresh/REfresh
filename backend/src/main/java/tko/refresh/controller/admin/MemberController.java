package tko.refresh.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.dto.admin.MemberSearchDto;
import tko.refresh.dto.admin.MemberUpdateDto;
import tko.refresh.repository.department.MemberDepartmentRepository;
import tko.refresh.service.admin.MemberService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/member")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    private final MemberService memberService;

    private final MemberDepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity getMemberAllList(@RequestParam(required = false) MemberSearchDto searchDto,
                                           @RequestParam int page) {
        int formatPage = page;

        Page<MemberDto> list;
        if(searchDto == null) {
            list = memberService.getAllMemberList(formatPage);
        } else {
            list = memberService.getSearchMemberList(searchDto, formatPage);
        }

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto> detailMember(@PathVariable String memberId) {
        MemberDto memberDto = memberService.getMemberDetail(memberId);

        return ResponseEntity.ok().body(memberDto);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<String> modifyMember(@PathVariable String memberId, @RequestBody MemberUpdateDto memberUpdateDto) {

        memberService.modifyMember(memberId, memberUpdateDto);

        return ResponseEntity.ok("Success");
    }

    @GetMapping("/new")
    public ResponseEntity getNewMemberForm() {

        List<String> list = departmentRepository.getDepartmentNameList();

        return ResponseEntity.ok().body(list);
    }

}
