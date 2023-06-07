package tko.refresh.util.jwt;

import static tko.refresh.domain.enu.RoleType.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;
import tko.refresh.domain.entity.Member;

@RequiredArgsConstructor
public class MemberDetailsImpl implements UserDetails {

    private Member member;
    private Collection<? extends GrantedAuthority> authorities;

    public MemberDetailsImpl(Member member) {
        this.member = member;
        this.authorities = new ArrayList<>();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setAuthorities(List<GrantedAuthority> authorityList) {
        this.authorities = authorityList;
    }
}
