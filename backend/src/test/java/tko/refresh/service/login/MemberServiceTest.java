package tko.refresh.service.login;


import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.json.JSONObject;
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
import tko.refresh.repository.calendar.DepartmentRepository;
import tko.refresh.repository.member.MemberRepository;

@SpringBootTest
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
@WithMockCustomUser
class MemberServiceTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
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
    void 로그인_테스트() throws Exception {
        // 요청 본문 데이터 생성
        JSONObject requestBody = new JSONObject();
        requestBody.put("memberId", "member");
        requestBody.put("password", "1234");

        MvcResult mvcResult = mockMvc.perform(post("/login")
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(requestBody.toString()))
                                     .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);

    }




}