package tko.refresh.repository.calendar;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.dto.calendar.request.GetAnnualByNameReqDto;
import tko.refresh.dto.calendar.response.GetAnnualByNameResDto;
import tko.refresh.repository.MemberRepository;

@DataJpaTest
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
class AnnualEmRepositoryTest {
    @Autowired
    private TestEntityManager tm;

    @Autowired
    private AnnualRepository annualRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AnnualEmRepository annualEmRepository;

    @BeforeEach
    void setUp() {
        Department department = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(),
                                               LocalDateTime.now());
        MemberInfo memberInfo = new MemberInfo("name1245", "012-1211-2124", "position@gmail.com");
        Member member = new Member("id", "1234", memberInfo, 15, MemberStatus.IN_USE, department,
                                   LocalDateTime.now(), LocalDateTime.now(), "dds", "sdds");
        department.addMember(member);
        member.setDepartment(department);

        Period period = Period.builder().startDate(LocalDateTime.now()).endDate(LocalDateTime.now()).build();
        Annual annual = new Annual(member, AnnualType.ANNUAL_LEAVE, AnnualStatus.AGREE, "SDds", period, "dds",
                                   "sdfds", LocalDateTime.now(), LocalDateTime.now());
        member.addAnnual(annual);
        annual.setMember(member);

        departmentRepository.save(department);
        memberRepository.save(member);
        annualRepository.save(annual);
    }

    @Test
    public void 부서_이름으로_연차정보_가져오기() {
        // when
        Page<GetAnnualByNameResDto> annualByMember = annualEmRepository.findAnnualByDept(
                GetAnnualByNameReqDto
                        .builder()
                        .name("개발팀")
                        .pageable(Pageable.ofSize(10).withPage(0)
                        ).build());
        // then
        assertThat(annualByMember.getContent().size()).isEqualTo(1);

    }

    @Test
    public void 사원_이름으로_연차정보_가져오기() {
        // given
        // when
        Page<GetAnnualByNameResDto> annualByMember = annualEmRepository.findAnnualByMember(
                GetAnnualByNameReqDto
                        .builder()
                        .name("name1245")
                        .pageable(Pageable.ofSize(10).withPage(0))
                        .build());
        // then
        assertThat(annualByMember.getContent().size()).isEqualTo(1);
    }
}