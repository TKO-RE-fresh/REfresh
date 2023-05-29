package tko.refresh.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.service.admin.MemberService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity getMemberAllList() {
        List<MemberDto> list = memberService.getAllMemberList(0);

        return ResponseEntity.ok().body(list);
    }
}
