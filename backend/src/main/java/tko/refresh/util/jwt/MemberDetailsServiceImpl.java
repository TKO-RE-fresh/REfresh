package tko.refresh.util.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import lombok.RequiredArgsConstructor;
import tko.refresh.domain.entity.Member;
import tko.refresh.repository.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findLoginMemberId(userId)
                                        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        MemberDetailsImpl memberDetails = new MemberDetailsImpl(member);

        List<GrantedAuthority> authorityList = new ArrayList<>();
        if(member.getMemberAuth() == RoleType.ADMIN) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if(member.getMemberStatus() == MemberStatus.NOT_IN_USE) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_LEAVE"));
        } else {
            authorityList.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        }

        memberDetails.setMember(member);
        memberDetails.setAuthorities(authorityList);

        return memberDetails;
    }


}
