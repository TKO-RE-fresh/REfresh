package tko.refresh.controller.login;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.member.MemberLoginDto;
import tko.refresh.service.member.MemberService;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    @PostMapping
    public String login(@ModelAttribute MemberLoginDto dto, HttpServletResponse response) {
        memberService.login(dto, response);
        return "login";
    }
}
