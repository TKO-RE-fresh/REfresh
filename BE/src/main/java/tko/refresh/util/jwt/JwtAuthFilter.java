package tko.refresh.util.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tko.refresh.dto.GlobalResponseDto;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    // HTTP 요청이 오면 WAS(tomcat)가 HttpServletRequest, HttpServletResponse 객체를 만들고
    // 만든 인자 값을 받아온다.
    // 요청이 들어오면 diFilterInternal 이 딱 한번 실행된다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Access / Refresh 헤더에서 토큰을 가져옴.
        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");

        if(accessToken != null) {
            // Access 토큰이 유효한지 확인
            if(jwtUtil.tokenValidation(accessToken)) {
                setAuthentication(jwtUtil.getEmailFromToken(accessToken));
            } else if(refreshToken != null) {
                // Access 토큰이 만료되었고, Refresh 토큰이 유효한지 확인
                if(jwtUtil.tokenValidation(refreshToken)) {
                    // Refresh 토큰이 유효하면 Access 토큰 재발급
                    String email = jwtUtil.getEmailFromToken(refreshToken);
                    setAuthentication(email);
                    String newAccessToken = jwtUtil.createToken(email, "Access");
                    response.setHeader("Access_Token", newAccessToken);
                } else {
                    jwtExceptionHandler(response, "Refresh 토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
                    return;
                }
            }

        }

        filterChain.doFilter(request, response);
    }

    // SecurityContext 에 Authentication 객체를 저장합니다.
    public void setAuthentication(String email) {
        Authentication authentication = jwtUtil.createAuthentication(email);
        // security가 만들어주는 securityContextHolder 그 안에 authentication을 넣어준다.
        // security가 securitycontextholder에서 인증 객체를 확인하는데
        // jwtAuthfilter에서 authentication을 넣어주면 UsernamePasswordAuthenticationFilter 내부에서 인증이 된 것을 확인하고 추가적인 작업을 진행하지 않는다
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Jwt 예외처리
    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new GlobalResponseDto(msg, status.value()));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
