package tko.refresh.util.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.dto.member.AuthMemberDto;
import tko.refresh.repository.calendar.DepartmentRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.service.login.WithMockCustomUser;

@WithMockCustomUser
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
@SpringBootTest
class JwtAuthMemberTest {

    @Autowired
    private JwtAuthMember jwtAuthMember;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Department department = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(), LocalDateTime.now());
        MemberInfo memberInfo = new MemberInfo("name1245", "012-1211-2124", "position@gmail.com");
        String encode = passwordEncoder.encode("1234");
        Member member = new Member("member", encode, memberInfo, 15, MemberStatus.IN_USE, RoleType.ADMIN , department, LocalDateTime.now(), LocalDateTime.now(), null, "sdds", "sdds");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        departmentRepository.save(department);
        memberRepository.save(member);
    }


    @Test
    void jwt_인증_멤버_스프링시큐리티에서_가져오기() throws Exception{
        AuthMemberDto authMember = jwtAuthMember.getJwtAuthMember();

        Assertions.assertThat(authMember.getMemberName()).isEqualTo("name1245");
    }
}