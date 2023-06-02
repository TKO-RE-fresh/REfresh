package tko.refresh.util.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tko.refresh.domain.entity.RefreshToken;
import tko.refresh.dto.member.TokenDto;
import tko.refresh.repository.redis.RedisRepository;
import tko.refresh.repository.refreshtoken.RefreshTokenRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final MemberDetailsServiceImpl memberDetailsService;
    private final RedisRepository redisRepository;

    @Value("${spring.jwt.live.atk}")
    private long ACCESS_TIME;
    @Value("${spring.jwt.live.rtk}")
    private long REFRESH_TIME;
    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";

    @Value("${spring.jwt.key}")
    private String secretKey;
    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // bean으로 등록 되면서 딱 한번 실행
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오는 기능
    public String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) :request.getHeader(REFRESH_TOKEN);
    }

    // 토큰 생성
    public TokenDto createAllToken(String email) {
        return TokenDto.builder()
                       .accessToken(createToken(email, "Access"))
                       .refreshToken(createToken(email, "Refresh"))
                       .build();
    }

    // 토큰 재발급
    public TokenDto reissueToken(String refreshToken) throws JsonProcessingException {
        String rtkInRedis = redisRepository.getValues(getEmailFromToken(refreshToken));
        if(Objects.isNull(rtkInRedis)) {
            throw new RuntimeException("Refresh Token이 만료되었습니다.");
        }

        String atk = createToken(getEmailFromToken(refreshToken), "Access");
        return TokenDto.builder().accessToken(atk).refreshToken(null).build();

    }



    public String createToken(String email, String type) {

        Date date = new Date();

        long time = type.equals("Access") ? ACCESS_TIME : REFRESH_TIME;

        return Jwts.builder()
                   .setSubject(email)
                   .setExpiration(new Date(date.getTime() + time))
                   .setIssuedAt(date)
                   .signWith(key, signatureAlgorithm)
                   .compact();

    }

    // Refresh 토큰 검증
    public Boolean tokenValidation(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    // refreshToken 토큰 검증
    // redis에 저장된 토큰과 비교
    public Boolean refreshTokenValidation(String token) {

        // 1차 토큰 검증
        if(!tokenValidation(token)) return false;

        String email = getEmailFromToken(token);
        // DB에 저장한 토큰 비교
        String refreshToken = redisRepository.getValues(email);
        if(refreshToken == null) return false;

        return token.equals(refreshToken);
    }

    // 토큰에서 email 가져오는 기능
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String email) {
        UserDetails userDetails = memberDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 엑세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Access_Token", accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("Refresh_Token", refreshToken);
    }
}
