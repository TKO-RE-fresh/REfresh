package tko.refresh.util.jwt;


import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import tko.refresh.domain.entity.Member;
@Component
public class JwtAuthMember {
    public Optional<Member> getJwtAuthMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if(principal instanceof UserDetails) {
                MemberDetailsImpl memberDetails = (MemberDetailsImpl) principal;
                return Optional.of(memberDetails.getMember());
            }
        }
        return Optional.empty();
    }


}
