package tko.refresh.service.login;
import static org.springframework.http.HttpStatus.*;
<<<<<<< HEAD
import static tko.refresh.domain.enu.RedisData.*;
import static tko.refresh.domain.enu.RedisData.MEMBER_AUTH;
import static tko.refresh.domain.enu.RedisData.MEMBER_NAME;
import static tko.refresh.util.jwt.JwtUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import tko.refresh.dto.GlobalResponseDto;
import tko.refresh.dto.member.AuthMemberDto;
import tko.refresh.dto.member.MemberJoinDto;
import tko.refresh.dto.member.request.MemberLoginReqDto;
import tko.refresh.dto.member.TokenDto;
import tko.refresh.dto.member.response.MemberLoginResDto;
import tko.refresh.repository.calendar.DepartmentRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.util.jwt.JwtAuthMember;
import tko.refresh.util.jwt.redis.RedisTokenRepository;
=======

import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.entity.RefreshToken;
import tko.refresh.dto.member.request.MemberLoginReqDto;
import tko.refresh.dto.member.TokenDto;
import tko.refresh.dto.member.response.MemberLoginResDto;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.repository.refreshtoken.RefreshTokenRepository;
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
import tko.refresh.util.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class LoginService {
<<<<<<< HEAD
=======

>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

<<<<<<< HEAD
    private final DepartmentRepository departmentRepository;

    private final RedisTokenRepository redisRepository;

    private final JwtAuthMember jwtAuthMember;

    @Transactional
    public GlobalResponseDto signup(MemberJoinDto dto) {
        // id 중복검사
        if(memberRepository.findByMemberId(dto.getMemberId()).isPresent()){
            return GlobalResponseDto.builder().message("중복된 아이디입니다.").statusCode(BAD_REQUEST.value()).build();
        }
        // email 중복검사
        if(memberRepository.findByLoginEmail(dto.getMemberEmail()).isPresent()){
            return GlobalResponseDto.builder().message("중복된 이메일입니다.").statusCode(BAD_REQUEST.value()).build();
        }

        Department dept = departmentRepository.findByName(dto.getDepartmentName());

        AuthMemberDto authMemberDto = jwtAuthMember.getJwtAuthMember();

        // 패스워드 암호화
        dto.setEncodePwd(passwordEncoder.encode(dto.getPassword()));
        Member member = Member.builder()
                .memberId(dto.getMemberId())
                .password(dto.getPassword())
                .memberAuth(dto.getMemberAuth())
                .memberInfo(MemberInfo.builder()
                            .name(dto.getMemberName())
                            .email(dto.getMemberEmail())
                            .cellphone(dto.getMemberCellphone())
                            .build())
                .memberStatus(dto.getMemberStatus())
                .annualCount(dto.getAnnualCount())
                .department(dept)
                .createdDate(dateFormat(dto.getCreatedDate()))
                .modifiedBy(authMemberDto.getMemberId())
                .modifiedDate(LocalDateTime.now())
                .createdBy(authMemberDto.getMemberId())
                .build();

        System.out.println("!!!!!"+member);

        dept.addMember(member);
        // 회원가입 성공
        memberRepository.save(member);
        return GlobalResponseDto.builder().message("회원가입 성공").statusCode(OK.value()).build();
    }
=======
    private final RefreshTokenRepository refreshTokenRepository;

//    @Transactional
//    public GlobalResponseDto signup(MemberJoinDto dto) {
//        // id 중복검사
//        if(memberRepository.findByMemberId(dto.getId()).isPresent()){
//            return GlobalResponseDto.builder().msg("중복된 아이디입니다.").statusCode(BAD_REQUEST.value()).build();
//        }
//        // email 중복검사
//        if(memberRepository.findByEmail(dto.getEmail()).isPresent()){
//            return GlobalResponseDto.builder().msg("중복된 이메일입니다.").statusCode(BAD_REQUEST.value()).build();
//        }
//
//
//        // 패스워드 암호화
//        dto.setEncodePwd(passwordEncoder.encode(dto.getPassword()));
//        Member member = Member.builder().memberDto(dto)
//                              .name("하이")
//                              .email("now20412041@gmail.com")
//                              .memberStatus(MemberStatus.IN_USE)
//                              .annualCount(15.0)
//                              .cellphone("010-2322-0111")
//                              .modifiedDate(LocalDateTime.now())
//                .createdDate(LocalDateTime.now())
//                .modifiedBy("admin")
//                .modifiedDate(LocalDateTime.now())
//                .createdBy("admin").build();
//
//        // 회원가입 성공
//        memberRepository.save(member);
//        return GlobalResponseDto.builder().msg("회원가입 성공").statusCode(OK.value()).build();
//    }
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3

    @Transactional
    public MemberLoginResDto login(MemberLoginReqDto loginDto, HttpServletResponse response) {

<<<<<<< HEAD

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
        System.out.println("유효한 계정");
        // 아이디 정보로 Token생성
        TokenDto tokenDto = jwtUtil.createAllToken(member.getMemberId(), member.getMemberInfo().getEmail());
        // 레디스에 정보 저장
        redisRegistry(member, tokenDto);
        addTokenCookieToResponse(response, tokenDto.getRefreshToken());
        // response 헤더에 Access Token
        setHeaderToken(response, tokenDto);
=======
        // 아이디 검사
        Member member = memberRepository.findLoginMemberId(loginDto.getMemberId()).get();
        if(member == null) {
            return MemberLoginResDto.builder().statusCode(UNAUTHORIZED.value()).message("아이디가 올바르지 않습니다.").build();
        }


        // 비밀번호 검사
        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            return MemberLoginResDto.builder().statusCode(UNAUTHORIZED.value()).message("비밀번호가 올바르지 않습니다.").build();
        }

        // 아이디 정보로 Token생성
        TokenDto tokenDto = jwtUtil.createAllToken(loginDto.getMemberId());

        // Refresh토큰 있는지 확인
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(member.getMemberInfo().getEmail());

        // 있다면 새토큰 발급후 업데이트
        // 없다면 새로 만들고 디비 저장
        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken(),
                                                                                member.getMemberId()));
        }else {
            RefreshToken newToken = RefreshToken.builder()
                    .accountEmail(member.getMemberInfo().getEmail())
                    .refreshToken(tokenDto.getRefreshToken()).build();
            refreshTokenRepository.save(newToken);
        }

        // response 헤더에 Access Token / Refresh Token 넣음
        setHeaderToken(response, tokenDto);

>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
        return MemberLoginResDto.builder().statusCode(OK.value()).message("로그인 성공").auth(member.getMemberAuth().getValue()).memberId(
                member.getMemberId()).memberName(member.getMemberInfo().getName()).deptName(member.getDepartment().getName()).build();

    }
<<<<<<< HEAD
    // 토큰 값을 쿠키로 설정하여 HTTP 응답에 추가하는 메소드
    public void addTokenCookieToResponse(HttpServletResponse response, String token) {
        Cookie tokenCookie = new Cookie(REFRESH_TOKEN, token);
//        tokenCookie.setHttpOnly(true);

//        tokenCookie.setSecure(true); HTTPS 연결에서만 전송
        tokenCookie.setPath("/"); // 쿠키의 유효 경로
//        tokenCookie.setComment("SameSite=Origin"); // CSRF 공격 방지
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

    public LocalDateTime dateFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return localDateTime;
    }
=======

    private void setHeaderToken(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }



>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3

}
