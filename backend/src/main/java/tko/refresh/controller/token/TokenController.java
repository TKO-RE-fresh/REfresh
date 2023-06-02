package tko.refresh.controller.token;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.member.TokenDto;
import tko.refresh.util.jwt.JwtUtil;
import tko.refresh.util.jwt.MemberDetailsImpl;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final MemberDetailsImpl memberDetails;
    private final JwtUtil jwtUtil;


    @GetMapping("/reissue")
    public TokenDto reissue(
            @AuthenticationPrincipal MemberDetailsImpl memberDetails
    ) throws JsonProcessingException {
        AccountResponse accountResponse = AccountResponse.of(memberDetails.getMember());
        return jwtUtil.reissueToken(accountResponse);
    }
}