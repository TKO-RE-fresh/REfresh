package tko.refresh.service.admin;

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
import tko.refresh.dto.admin.AnnualManageDto;

import tko.refresh.dto.admin.AnnualSearchDto;
import tko.refresh.repository.calendar.*;
import tko.refresh.repository.member.MemberRepository;

import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
class AnnualManageServiceTest {

    private Page<AnnualManageDto> list;

    @Autowired
    private AnnualRepository annualRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AnnualCountRepository annualCountRepository;

    @Autowired
    private AnnualManageService annualManageService;

    @BeforeEach
    public void setup(){
        Department department = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(), LocalDateTime.now());
        MemberInfo memberInfo = new MemberInfo("name1245", "012-1211-2124", "position@gmail.com");
        Member member = new Member("id", "1234", memberInfo, 15, MemberStatus.IN_USE, RoleType.MEMBER , department, LocalDateTime.now(), LocalDateTime.now(), null, "dds", "sdds");
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
    public void 관리자_연차관리정보_불러오기(){

        list = annualManageService.getSearchAnnualMangeList(AnnualSearchDto.builder().build(), 1);

        list.stream().forEach(System.out::println);

        Assertions.assertEquals(list.getContent().size(),1);
    }

    @Test
    public void 관리자_연차정보_검색_부서(){
        //given
        AnnualSearchDto searchDto = AnnualSearchDto
                .builder()
                .departmentName("개발팀")
                .build();

        list=annualManageService.getSearchAnnualMangeList(searchDto,1);
        list.forEach(System.out::println);

        Assertions.assertEquals(list.getContent().size(),1);

    }


    @Test
    public void 관리자_연차정보_검색_부서_상태(){
        //given
        AnnualSearchDto searchDto = AnnualSearchDto
                .builder()
                .status(AnnualStatus.AGREE)
                .departmentName("영업팀")
                .build();

        list=annualManageService.getSearchAnnualMangeList(searchDto,1);
        list.forEach(System.out::println);

        Assertions.assertEquals(list.getContent().size(),0);
    }

    @Test
    public void 주말_공휴일_제외_기간계산_메소드(){
        Period period = new Period(LocalDateTime.of(2023,05,26,0,0,0),LocalDateTime.of(2023, 5,26,0,0,0));
        int workingDay = annualManageService.WorkingDaysCounter(period);

        Assertions.assertEquals(workingDay,1);
    }

}