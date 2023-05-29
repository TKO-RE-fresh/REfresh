package tko.refresh.service.login;
import static org.springframework.http.HttpStatus.*;

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
import tko.refresh.util.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

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
                                                                                member.getMemberId()));
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
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }




}
