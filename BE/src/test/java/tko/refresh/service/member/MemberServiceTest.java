package tko.refresh.service.member;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import tko.refresh.WebSecurityConfig;
import tko.refresh.WithMockCustomUser;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.repository.calendar.DepartmentRepository;
import tko.refresh.repository.member.MemberRepository;

@SpringBootTest
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
class MemberServiceTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        Department department = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(), LocalDateTime.now());
        MemberInfo memberInfo = new MemberInfo("name1245", "012-1211-2124", "position@gmail.com");
        Member member = new Member("member", "1234", memberInfo, 15, MemberStatus.IN_USE, department, LocalDateTime.now(), LocalDateTime.now(), "dds", "sdds");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        departmentRepository.save(department);
        memberRepository.save(member);
    }


    @Test
    @WithMockCustomUser
    void 로그인_테스트() throws Exception {
        mockMvc.perform(post("/login").
                                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                                param("memberId", "member")
                                .param("password", "1234")).andExpect(
                status().isOk());

    }




}