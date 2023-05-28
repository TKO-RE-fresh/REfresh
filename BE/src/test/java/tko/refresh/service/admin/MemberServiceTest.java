package tko.refresh.service.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.repository.calendar.DepartmentRepository;
import tko.refresh.repository.member.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MemberService memberService;

    private List<MemberDto> list;

    @BeforeEach
    public void setup() {
        Department department = new Department("개발팀", "devzang", "admin", "admin", LocalDateTime.now(), LocalDateTime.now());
        MemberInfo memberInfo = new MemberInfo("홍길동", "010-3385-3838", "hgd@daum.net");
        Member member = Member.builder()
                .memberId("id")
                .password("1234")
                .memberInfo(memberInfo)
                .annualCount(15)
                .memberStatus(MemberStatus.IN_USE)
                .department(department)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .memberAuth(RoleType.MEMBER)
//                .retireDate(LocalDateTime.now())
                .createdBy("admin")
                .modifiedBy("admin")
                .build();
        department.addMember(member);
        member.setDepartment(department);

        departmentRepository.save(department);
        memberRepository.save(member);

    }

    @Test
    public void 관리자_사원전체조회() {
        list = memberService.getAllMemberList(0);
        list.stream().forEach(System.out::println);

        Assertions.assertEquals(list.size(), 1);
    }

}