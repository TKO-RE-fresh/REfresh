package tko.refresh.util.jwt;


import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.dto.member.AuthMemberDto;

@Component
public class JwtAuthMember {
    public AuthMemberDto getJwtAuthMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if(principal instanceof UserDetails) {
                MemberDetailsImpl memberDetails = (MemberDetailsImpl) principal;
                // 부서, id, 멤버 이름, 권한, email
                String memberId = memberDetails.getMember().getMemberId();
                String memberName = memberDetails.getMember().getMemberInfo().getName();
                String memberEmail = memberDetails.getMember().getMemberInfo().getEmail();
                String department = memberDetails.getMember().getDepartment().getName();
                RoleType memberAuth = memberDetails.getMember().getMemberAuth();
                return AuthMemberDto.builder()
                                    .department(department)
                                    .memberId(memberId)
                                    .memberName(memberName)
                                    .memberAuth(memberAuth)
                                    .memberEmail(memberEmail)
                                    .build();
            }
        }
        return AuthMemberDto.builder().build();
    }


}
