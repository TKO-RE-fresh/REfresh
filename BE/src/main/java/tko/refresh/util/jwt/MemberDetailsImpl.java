package tko.refresh.util.jwt;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.repository.member.MemberRepository;

@RequiredArgsConstructor
public class MemberDetailsImpl implements UserDetails {
    private Member member;
    private RoleType role;
    private Collection<? extends GrantedAuthority> authorities;


    public MemberDetailsImpl(Member member, RoleType role) {
        this.member = member;
        this.role = member.getRoleType();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(role.getValue()));
    }
    public RoleType getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
}
