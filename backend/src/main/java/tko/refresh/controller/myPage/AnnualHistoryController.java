package tko.refresh.controller.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tko.refresh.dto.admin.AnnualStatusDto;
import tko.refresh.dto.member.AuthMemberDto;
import tko.refresh.dto.myPage.AnnualHistoryDto;
import tko.refresh.dto.myPage.CancelDto;
import tko.refresh.dto.myPage.HistorySearchDto;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.service.myPage.AnnualHistoryService;
import tko.refresh.util.jwt.JwtAuthMember;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins="http://localhost:8080")
@RequiredArgsConstructor
@RequestMapping("/myPage/history")
@RestController
public class AnnualHistoryController {

    private final AnnualHistoryService annualHistoryService;
    private final MemberRepository memberRepository;
    private final JwtAuthMember jwtAuthMember;

    @GetMapping("/{page}")
    public ResponseEntity getAnnualHistoryList(@ModelAttribute HistorySearchDto searchDto , @PathVariable Optional<Integer> page){
        Page<AnnualHistoryDto> list = annualHistoryService.getAnnualHistoryPage(searchDto, page.orElse(1));
        return ResponseEntity.ok().body(list);
    }

    @PutMapping
    public ResponseEntity cancelAnnualRequest(@RequestBody CancelDto dto){
        boolean result = annualHistoryService.CancelAnnualRequest(dto.getUid());
        if(result){
            return ResponseEntity.ok("Success");
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/year")
    public ResponseEntity getMemberCreationYear(){

        AuthMemberDto authMember = jwtAuthMember.getJwtAuthMember();
        //로그인한 멤버 이름으로 변경
        int year = memberRepository.getMemberCreationYear(authMember.getMemberId());
        return ResponseEntity.ok().body(year);
    }
}
