package tko.refresh.util.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tko.refresh.dto.GlobalResponseDto;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final String ACCESS = "Access";
    private final String UNAUTHORIZED = "Unauthorized";

    private final MemberLogoutHandler memberLogoutHandler;



    // HTTP 요청이 오면 WAS(tomcat)가 HttpServletRequest, HttpServletResponse 객체를 만들고
    // 만든 인자 값을 받아온다.
    // 요청이 들어오면 diFilterInternal 이 딱 한번 실행된다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Access / Refresh 헤더에서 토큰을 가져옴.
        String accessToken = extractTokenFromHeader(jwtUtil.getHeaderToken(request));
        String refreshToken = jwtUtil.getCookieToken(request);

        if(accessToken == null  && refreshToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if(accessToken != null) {
            if (!accessToken.equals("undefined") && jwtUtil.tokenValidation(accessToken)) {
                String id = jwtUtil.getIdFromToken(accessToken);
                setAuthentication(id);
            } else {
                // Access 토큰이 만료된 경우
                if (refreshToken != null && jwtUtil.refreshTokenValidation(refreshToken)) {
                    // Refresh 토큰이 유효한 경우
                    String id = jwtUtil.getIdFromToken(refreshToken);
                    String email = jwtUtil.getEmailFromToken(refreshToken);
                    setAuthentication(id);
                    String newAccessToken = jwtUtil.createToken(id, email, ACCESS);
                    response.setHeader(jwtUtil.ACCESS_TOKEN, newAccessToken);
                } else {
                    // Refresh 토큰이 유효하지 않은 경우
                    if(!memberLogoutHandler.isLogout(request)) {
                        memberLogoutHandler.logout(request, response, null);
                    }
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }


    private String extractTokenFromHeader(String headerValue) {
        if (StringUtils.hasText(headerValue) && headerValue.startsWith("Bearer ")) {
            return headerValue.substring(7).equals("null") ? null : headerValue.substring(7);
        }
        return null;
    }

    // SecurityContext 에 Authentication 객체를 저장합니다.
    public void setAuthentication(String id) {
        Authentication authentication = jwtUtil.createAuthentication(id);
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
