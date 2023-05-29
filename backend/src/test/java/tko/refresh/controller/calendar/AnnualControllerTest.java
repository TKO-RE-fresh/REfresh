package tko.refresh.controller.calendar;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import tko.refresh.service.login.WithMockCustomUser;

@SpringBootTest
@TestPropertySource("classpath:application-TEST.properties")
@WithMockCustomUser
@Transactional
class AnnualControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;




    @BeforeEach
    void setUp() {
        Department department = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(), LocalDateTime.now());
        MemberInfo memberInfo = new MemberInfo("name1245", "012-1211-2124", "position@gmail.com");
        Member member = new Member("member", "1234", memberInfo, 15, MemberStatus.IN_USE, RoleType.MEMBER , department, LocalDateTime.now(), LocalDateTime.now(), null, "sdds", "sdds");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }


    private MockMvc mockMvc;

    @Test
    void 부서와_날짜로_연차_조회() throws Exception {

        mockMvc.perform(get("/calendar/annual").param("year", "2021").param("month", "1")
                                .param("day", "1").param("deptName", "개발팀").param("page","1")
                                .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void 이름으로_연차_조회() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/calendar/annual/member")
                                                      .param("name", "김민")
                                                      .param("page", "1")
                                                      .param("size", "10"))
                                     .andExpect(status().isOk())
                                     .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

}