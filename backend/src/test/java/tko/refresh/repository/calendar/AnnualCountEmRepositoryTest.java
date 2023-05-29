package tko.refresh.repository.calendar;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.AnnualCount;
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.dto.YearMonthDto;
import tko.refresh.dto.calendar.request.annualcount.AnnualCountReqDto;
import tko.refresh.dto.calendar.response.annualcount.AnnualCountResDto;
import tko.refresh.repository.member.MemberRepository;

@SpringBootTest
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
class AnnualCountEmRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AnnualRepository annualRepository;

    @Autowired
    private AnnualCountRepository annualCountRepository;


    @Autowired
    private AnnualCountEmRepository annualCountEmRepository;

    @BeforeEach
    void setUp() {
        Department department = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(), LocalDateTime.now());
        MemberInfo memberInfo = new MemberInfo("name1245", "012-1211-2124", "position@gmail.com");
        Member member = new Member("id", "1234", memberInfo, 15, MemberStatus.IN_USE, RoleType.MEMBER, department, LocalDateTime.now(), LocalDateTime.now(), null, "dds", "sdds");
        department.addMember(member);
        member.setDepartment(department);

        Period period = Period.builder().startDate(LocalDateTime.now()).endDate(LocalDateTime.now()).build();
        Annual annual = new Annual(member, AnnualType.ANNUAL_LEAVE, AnnualStatus.AGREE, "SDds", period, "dds", "sdfds", LocalDateTime.now(), LocalDateTime.now());


        AnnualCount annualCount = AnnualCount.builder().sumCount(0).annualDate(LocalDateTime.now()).department(department).build();
        department.getAnnualCount().add(annualCount);
        annualCount.setDepartment(department);
        member.addAnnual(annual);
        annual.setMember(member);


        departmentRepository.save(department);
        memberRepository.save(member);
        annualRepository.save(annual);
        annualCountRepository.save(annualCount);

    }

    @Test
    void 부서이름과_날짜_연차집계정보_가져오기() {
        // 실패
        List<AnnualCountResDto> list1 = annualCountEmRepository.getAnnualCountByDept(AnnualCountReqDto.builder()
                                                                                             .year(LocalDateTime.now().getYear() - 10)
                                                                                             .month(LocalDateTime.now().getMonth().getValue())
                                                                                            .deptName("개발팀")
                                                                                            .build());
        assertThat(list1.size()).isEqualTo(0);
        // 성공
        List<AnnualCountResDto> list2 = annualCountEmRepository.getAnnualCountByDept(AnnualCountReqDto.builder()
                                                                                                      .year(LocalDateTime.now().getYear())
                                                                                                      .month(LocalDateTime.now().getMonth().getValue())
                                                                                                     .deptName("개발팀")
                                                                                                     .build());
        assertThat(list2.size()).isEqualTo(1);
    }


}