package tko.refresh.controller.login;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.member.MemberJoinDto;
import tko.refresh.dto.member.request.MemberLoginReqDto;
import tko.refresh.dto.member.response.MemberLoginResDto;
import tko.refresh.service.login.LoginService;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService memberService;
    @PostMapping
    public ResponseEntity login(@RequestBody MemberLoginReqDto dto, HttpServletResponse response) {

        MemberLoginResDto inform = memberService.login(dto, response);

        return ResponseEntity.ok().body(inform);
    }

    @PostMapping("/join")
    public String join() {
        memberService.signup(MemberJoinDto.builder().deptName("인사팀").memberId("jaeseok").password("qwer1234").email("jaeseok@daum.net").build());
        return "success";
    }
}
