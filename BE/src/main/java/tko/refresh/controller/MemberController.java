package tko.refresh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tko.refresh.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/save")
    public String save() {
        memberService.save();
        return "success";
    }

}
