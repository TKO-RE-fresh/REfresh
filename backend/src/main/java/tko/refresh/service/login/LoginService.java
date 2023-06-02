package tko.refresh.service.login;
import static org.springframework.http.HttpStatus.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.entity.RefreshToken;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.dto.GlobalResponseDto;
import tko.refresh.dto.member.MemberJoinDto;
import tko.refresh.dto.member.request.MemberLoginReqDto;
import tko.refresh.dto.member.TokenDto;
import tko.refresh.dto.member.response.MemberLoginResDto;
import tko.refresh.repository.calendar.DepartmentRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.repository.refreshtoken.RefreshTokenRepository;
import tko.refresh.util.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final DepartmentRepository departmentRepository;

    private final RefreshTokenRepository refreshTokenRepository;

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
                .memberAuth(RoleType.ADMIN)
                              .memberInfo(MemberInfo.builder().name("홍승희").email(dto.getEmail()).cellphone("010-7877-7123").build())
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
                                                                                member.getMemberInfo().getEmail()));
        }else {
            RefreshToken newToken = RefreshToken.builder()
                    .accountEmail(member.getMemberInfo().getEmail())
                    .refreshToken(tokenDto.getRefreshToken()).build();
            refreshTokenRepository.save(newToken);
        }

        // response 헤더에 Access Token / Refresh Token 넣음
        setHeaderToken(response, tokenDto);

        return MemberLoginResDto.builder().statusCode(OK.value()).message("로그인 성공").auth(member.getMemberAuth().getValue()).memberId(
                member.getMemberId()).memberName(member.getMemberInfo().getName()).deptName(member.getDepartment().getName()).build();

    }

    private void setHeaderToken(HttpServletResponse response, TokenDto tokenDto) {
        response.setHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.setHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }




}
