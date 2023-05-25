package tko.refresh.repository.calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
import tko.refresh.repository.MemberRepository;

@DataJpaTest
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
class AnnualCountRepositoryImplTest {

    @Autowired
    private AnnualRepository annualRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AnnualRepositoryImpl annualRepositoryImpl;
    @Autowired
    private AnnualCountRepository annualCountRepository;
    @Autowired
    private AnnualCountRepositoryImpl annualCountRepositoryImpl;

    @BeforeEach
    void setUp() {
        Department department = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(), LocalDateTime.now());
        MemberInfo memberInfo = new MemberInfo("name1245", "012-1211-2124", "position@gmail.com");
        Member member = new Member("id", "1234", memberInfo, 15, MemberStatus.IN_USE, department, LocalDateTime.now(), LocalDateTime.now(), "dds", "sdds");
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
        //given
        List<AnnualCount> list = annualCountRepositoryImpl.getAnnualCountByDept("2023", "5", "개발팀");
        assertThat(list.size()).isEqualTo(1);
    }


}