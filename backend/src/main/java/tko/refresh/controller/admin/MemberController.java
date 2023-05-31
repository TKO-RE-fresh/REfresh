package tko.refresh.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import tko.refresh.domain.entity.Member;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.dto.admin.MemberSearchDto;
import tko.refresh.dto.admin.MemberUpdateDto;
import tko.refresh.service.admin.MemberService;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{page}")
    public ResponseEntity getMemberAllList(@RequestParam(required = false) MemberSearchDto searchDto,
                                           @PathVariable Optional<Integer> page) {
        int formatPage = page.orElse(0);

        Page<MemberDto> list;
        if(searchDto == null) {
            list = memberService.getAllMemberList(formatPage);
        } else {
            list = memberService.getSearchMemberList(searchDto, formatPage);
        }

        return ResponseEntity.ok().body(list);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<String> modifyMember(@PathVariable String memberId, @RequestBody MemberUpdateDto memberUpdateDto) {

        memberService.modifyMember(memberId, memberUpdateDto);

        return ResponseEntity.ok("Success");
    }

}
