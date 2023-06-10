package tko.refresh.service.myPage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
import tko.refresh.dto.myPage.AnnualHistoryDto;
import tko.refresh.dto.myPage.HistorySearchDto;
import tko.refresh.repository.calendar.AnnualCountRepository;
import tko.refresh.repository.calendar.AnnualRepository;
import tko.refresh.repository.calendar.DepartmentRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.service.admin.AnnualManageService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
class AnnualHistoryServiceTest {

    @Autowired
    private AnnualRepository annualRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AnnualCountRepository annualCountRepository;

    @Autowired
    private AnnualHistoryService annualHistoryService;

    @BeforeEach
    public void setup(){
        Department department = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(), LocalDateTime.now());
        MemberInfo memberInfo = new MemberInfo("name1245", "012-1211-2124", "position@gmail.com");
        Member member = new Member("juhee", "1234", memberInfo, 15, MemberStatus.IN_USE, RoleType.MEMBER , department, LocalDateTime.now(), LocalDateTime.now(), null, "dds", "sdds");
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

    private Page<AnnualHistoryDto> list;

    @Test
    public void 마이페이지_연차내역_전체조회(){
        list = annualHistoryService.getAnnualHistoryPage(HistorySearchDto.builder().build(),1);

        list.stream().forEach(System.out::println);
        Assertions.assertEquals(list.getContent().size(),1);

    }

    @Test
    public void 마이페이지_연차내역_연도조회(){
        HistorySearchDto searchDto = HistorySearchDto
                .builder()
                .year(2022)
                .build();
        list = annualHistoryService.getAnnualHistoryPage(searchDto,1);
        list.stream().forEach(System.out::println);
        Assertions.assertEquals(list.getContent().size(),0);
    }


    @Test
    public void 마이페이지_연차내역_연도_상태조회(){
        HistorySearchDto searchDto = HistorySearchDto
                .builder()
                .year(2023)
                .status(AnnualStatus.AGREE)
                .build();
        list = annualHistoryService.getAnnualHistoryPage(searchDto,1);
        list.stream().forEach(System.out::println);
        Assertions.assertEquals(list.getContent().size(),1);
    }
}