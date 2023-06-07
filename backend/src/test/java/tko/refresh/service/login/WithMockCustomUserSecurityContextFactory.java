package tko.refresh.service.login;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.util.jwt.JwtAuthenticationToken;
import tko.refresh.util.jwt.JwtUtil;
import tko.refresh.util.jwt.MemberDetailsImpl;
import tko.refresh.util.jwt.MemberDetailsServiceImpl;

public class WithMockCustomUserSecurityContextFactory implements
                                                      WithSecurityContextFactory<WithMockCustomUser> {

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Department department = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(), LocalDateTime.now());
        MemberInfo memberInfo = new MemberInfo("name1245", "012-1211-2124", "position@gmail.com");
        Member m = new Member("member", "1234", memberInfo, 15, MemberStatus.IN_USE, RoleType.ADMIN ,department, LocalDateTime.now(), LocalDateTime.now(), null, "sdds", "sdds");

        MemberDetailsImpl member = new MemberDetailsImpl(m);
        // 필요한 권한 설정
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        member.setAuthorities(authorities);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(member, null,
                member.getAuthorities());

        context.setAuthentication(authentication);
        return context;
    }

    private String generateJwtToken(MemberDetailsImpl member) {
        return jwtUtil.createToken(member.getMember().getMemberId(), member.getMember().getMemberInfo().getEmail() , JwtUtil.ACCESS_TOKEN);
    }
}
