package tko.refresh.util.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class MemberLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        SecurityContextHolder.clearContext();
    }

    public boolean isLogout(HttpServletRequest request) {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
