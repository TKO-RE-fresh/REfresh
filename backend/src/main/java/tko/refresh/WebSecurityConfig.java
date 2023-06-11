package tko.refresh;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import tko.refresh.util.jwt.JwtAuthFilter;
import tko.refresh.util.jwt.JwtUtil;
import tko.refresh.util.jwt.MemberDetailsServiceImpl;
import tko.refresh.util.jwt.MemberLogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;


    @Bean
    public UserDetailsService userDetailsService() {
        // 역할 정보를 제공하는 사용자 정의 UserDetailsService를 반환
        return new MemberDetailsServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public MemberLogoutHandler memberLogoutHandler() {
        return new MemberLogoutHandler();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return webSecurity -> webSecurity.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080")); // 80 포트에서 오는 요청만 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "OPTIONS")); // 요청 메서드 허용
        configuration.setAllowedHeaders(Arrays.asList("Cache-Control", "Content-Type", "access_token")); // 허용하는 헤더
        configuration.setExposedHeaders(Arrays.asList("Cache-Control", "Content-Type", "access_token"));
        configuration.setAllowCredentials(true); // 쿠키 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 URL에 대해 설정 적용

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        MemberLogoutHandler logoutHandler = memberLogoutHandler();
        http.cors();
//        http.requiresChannel().anyRequest().requiresSecure();
        http.logout().addLogoutHandler(logoutHandler);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**", "/swagger-ui.html", "/swagger/**","/token/**",
                                             "/swagger-resources/**","/webjars/**","/v2/api-docs").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").access("hasAnyRole('MEMBER', 'ADMIN')")
            .anyRequest().authenticated().and()
//            .headers().frameOptions().sameOrigin().and()
            .addFilterBefore(new JwtAuthFilter(jwtUtil, logoutHandler), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
