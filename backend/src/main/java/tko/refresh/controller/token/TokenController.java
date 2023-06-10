package tko.refresh.controller.token;

import static org.springframework.http.HttpStatus.*;
import static tko.refresh.util.jwt.JwtUtil.*;
import static tko.refresh.util.jwt.JwtUtil.ACCESS_TOKEN;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import tko.refresh.dto.member.response.MemberLoginResDto;
import tko.refresh.util.jwt.JwtAuthMember;
import tko.refresh.util.jwt.JwtUtil;
import tko.refresh.util.jwt.MemberLogoutHandler;
import tko.refresh.util.jwt.redis.RedisTokenRepository;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtUtil jwtUtil;
    private final JwtAuthMember jwtAuthMember;
    private final String COOKIE_TOKEN_KEY = "refresh_token";
    private final RedisTokenRepository redisTokenRepository;

    private final MemberLogoutHandler memberLogoutHandler;

    @PostMapping("/cookie")
    public ResponseEntity getCookieToken(@CookieValue("Refresh_Token") String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        if(!refreshToken.isEmpty() && !jwtUtil.refreshTokenValidation(refreshToken)) {
            return ResponseEntity.status(BAD_REQUEST).body("만료된 토큰입니다.");
        }

        String redisToken = (String) redisTokenRepository.getValues(jwtUtil.getEmailFromToken(refreshToken), REFRESH_TOKEN);

        if (redisToken != null && redisToken.equals(refreshToken)) {
            String id = jwtUtil.getIdFromToken(refreshToken);
            String email = jwtUtil.getEmailFromToken(refreshToken);
            String newAccessToken = jwtUtil.createToken(id, email, ACCESS_TOKEN);
            MemberLoginResDto memberData = getMemberDataFromRedis(email);
            return ResponseEntity.ok().header(ACCESS_TOKEN, newAccessToken).body(memberData);
        } else {
            memberLogoutHandler.logout(request, response, null);
            return ResponseEntity.status(BAD_REQUEST).body("만료된 토큰입니다.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@CookieValue("Refresh_Token") String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        if(!jwtUtil.tokenValidation(refreshToken)) {
            return ResponseEntity.status(BAD_REQUEST).body("만료된 토큰입니다.");
        }
        memberLogoutHandler.logout(request, response, null);
        destroyCookie(COOKIE_TOKEN_KEY, response);
        String email = jwtUtil.getEmailFromToken(refreshToken);
        redisTokenRepository.deleteValues(email);
        return ResponseEntity.ok().body("로그아웃 되었습니다.");
    }

    private void destroyCookie(String cookieName, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private MemberLoginResDto getMemberDataFromRedis(String email) {
        String memberId = (String) redisTokenRepository.getValues(email, "memberId");
        String memberName = (String) redisTokenRepository.getValues(email, "memberName");
        String deptName = (String) redisTokenRepository.getValues(email, "memberDept");
        String auth = (String) redisTokenRepository.getValues(email, "memberAuth");
        return MemberLoginResDto.builder().auth(auth).deptName(deptName).memberId(memberId).memberName(memberName)
                                .message("유효한 리프레시 토큰").statusCode(OK.value()).build();
    }

}