package tko.refresh.util.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tko.refresh.domain.entity.Member;
import tko.refresh.repository.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(userId)
                                        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        MemberDetailsImpl memberDetails = new MemberDetailsImpl(member, member.getMemberAuth());
        memberDetails.setMember(member);
        return memberDetails;
    }

}
