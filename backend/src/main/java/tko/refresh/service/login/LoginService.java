package tko.refresh.service.login;
import static org.springframework.http.HttpStatus.*;
import static tko.refresh.domain.enu.RedisData.*;
import static tko.refresh.domain.enu.RedisData.MEMBER_AUTH;
import static tko.refresh.domain.enu.RedisData.MEMBER_NAME;
import static tko.refresh.util.jwt.JwtUtil.*;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.dto.GlobalResponseDto;
import tko.refresh.dto.member.MemberJoinDto;
import tko.refresh.dto.member.request.MemberLoginReqDto;
import tko.refresh.dto.member.TokenDto;
import tko.refresh.dto.member.response.MemberLoginResDto;
import tko.refresh.repository.calendar.DepartmentRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.util.jwt.redis.RedisTokenRepository;
import tko.refresh.util.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final DepartmentRepository departmentRepository;

    private final RedisTokenRepository redisRepository;

    @Transactional
    public GlobalResponseDto signup(MemberJoinDto dto) {
        // id 중복검사
        if(memberRepository.findByMemberId(dto.getMemberId()).isPresent()){
            return GlobalResponseDto.builder().message("중복된 아이디입니다.").statusCode(BAD_REQUEST.value()).build();
        }
        // email 중복검사
        if(memberRepository.findByLoginEmail(dto.getEmail()).isPresent()){
            return GlobalResponseDto.builder().message("중복된 이메일입니다.").statusCode(BAD_REQUEST.value()).build();
        }
        Department dept = departmentRepository.findByName(dto.getDeptName());

        // 패스워드 암호화
        dto.setEncodePwd(passwordEncoder.encode(dto.getPassword()));
        Member member = Member.builder()
                .memberId(dto.getMemberId())
                .password(dto.getPassword())
                .memberAuth(RoleType.MEMBER)
                              .memberInfo(MemberInfo.builder().name("김창헌").email(dto.getEmail()).cellphone("010-4537-7123").build())
                              .memberStatus(MemberStatus.IN_USE)
                              .annualCount(15.0)
                              .modifiedDate(LocalDateTime.now())
                .department(dept)
                .createdDate(LocalDateTime.now())
                .modifiedBy("admin")
                .modifiedDate(LocalDateTime.now())
                .createdBy("admin").build();
        dept.addMember(member);
        // 회원가입 성공
        memberRepository.save(member);
        return GlobalResponseDto.builder().message("회원가입 성공").statusCode(OK.value()).build();
    }

    @Transactional
    public MemberLoginResDto login(MemberLoginReqDto loginDto, HttpServletResponse response) {


        // 아이디 검사
        Optional<Member> existMember = memberRepository.findByMemberId(loginDto.getMemberId());
        if(existMember.isEmpty()) {
            return MemberLoginResDto.builder().statusCode(FORBIDDEN.value()).message("id").build();
        }

        Member member = existMember.get();

        // 비밀번호 검사
        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            return MemberLoginResDto.builder().statusCode(FORBIDDEN.value()).message("pwd").build();
        }
        //--유효한 계정--

        // 아이디 정보로 Token생성
        TokenDto tokenDto = jwtUtil.createAllToken(member.getMemberId(), member.getMemberInfo().getEmail());
        // 레디스에 정보 저장
        redisRegistry(member, tokenDto);
        addTokenCookieToResponse(response, tokenDto.getRefreshToken());
        // response 헤더에 Access Token
        setHeaderToken(response, tokenDto);
        return MemberLoginResDto.builder().statusCode(OK.value()).message("로그인 성공").auth(member.getMemberAuth().getValue()).memberId(
                member.getMemberId()).memberName(member.getMemberInfo().getName()).deptName(member.getDepartment().getName()).build();

    }
    // 토큰 값을 쿠키로 설정하여 HTTP 응답에 추가하는 메소드
    public void addTokenCookieToResponse(HttpServletResponse response, String token) {
        Cookie tokenCookie = new Cookie(REFRESH_TOKEN, token);
        tokenCookie.setHttpOnly(true);
//        tokenCookie.setSecure(true); HTTPS 연결에서만 전송
        tokenCookie.setPath("/"); // 쿠키의 유효 경로
        tokenCookie.setComment("SameSite=Origin"); // CSRF 공격 방지
        tokenCookie.setMaxAge(7776000); // 쿠키의 만료 시간 (초 단위) 3개월
        response.addCookie(tokenCookie);
    }


    private void redisRegistry(Member member, TokenDto tokenDto) {
        redisRepository.setValues(member.getMemberInfo().getEmail(), REFRESH_TOKEN , tokenDto.getRefreshToken());
        redisRepository.setValues(member.getMemberInfo().getEmail(), MEMBER_ID.getName(), member.getMemberId());
        redisRepository.setValues(member.getMemberInfo().getEmail(), MEMBER_AUTH.getName(), member.getMemberAuth().getValue());
        redisRepository.setValues(member.getMemberInfo().getEmail(), MEMBER_NAME.getName(), member.getMemberInfo().getName());
        redisRepository.setValues(member.getMemberInfo().getEmail(), MEMBER_DEPT.getName(), member.getDepartment().getName());
    }

    private void setHeaderToken(HttpServletResponse response, TokenDto tokenDto) {
        response.setHeader(ACCESS_TOKEN, tokenDto.getAccessToken());
    }




}
