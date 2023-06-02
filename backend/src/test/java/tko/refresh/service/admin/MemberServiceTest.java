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
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.dto.admin.MemberSearchDto;
import tko.refresh.dto.admin.MemberUpdateDto;
import tko.refresh.repository.calendar.DepartmentRepository;
import tko.refresh.repository.member.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
//                .retireDate(LocalDateTime.now())
                .memberAuth(RoleType.MEMBER)
                .createdBy("admin")
                .modifiedBy("admin")
                .build();
        department.addMember(member);
        member.setDepartment(department);

        departmentRepository.save(department);
        memberRepository.save(member);

        Department department2 = new Department("인사팀", "insazang", "admin", "admin", LocalDateTime.now(), LocalDateTime.now());
        MemberInfo memberInfo2 = new MemberInfo("김예준", "010-3442-3838", "insa@daum.net");
        Member member2 = Member.builder()
                .memberId("insa")
                .password("1234")
                .memberInfo(memberInfo2)
                .annualCount(15)
                .memberStatus(MemberStatus.IN_USE)
                .department(department2)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
//                .retireDate(LocalDateTime.now())
                .memberAuth(RoleType.MEMBER)
                .createdBy("admin")
                .modifiedBy("admin")
                .build();
        department2.addMember(member2);
        member2.setDepartment(department2);

        departmentRepository.save(department);
        memberRepository.save(member2);
    }

    @Test
    public void 관리자_사원전체조회() {
        Page<MemberDto> list = memberService.getAllMemberList(1);
        list.stream().forEach(System.out::println);

        Assertions.assertEquals(list.getTotalElements(), 2);
    }

    @Test
    public void 관리자_사원검색_이름() {
        MemberSearchDto searchDto = MemberSearchDto
                .builder()
                .memberName("홍길동")
                .build();

        Page<MemberDto> list = memberService.getSearchMemberList(searchDto, 1);

        list.forEach(System.out::println);
        System.out.println(list.getContent());

        Assertions.assertEquals(list.getTotalElements(), 1);
    }

    @Test
    public void 관리자_사원검색_부서() {
        MemberSearchDto searchDto = MemberSearchDto
                .builder()
                .departmentName("인사팀")
                .build();

        Page<MemberDto> list = memberService.getSearchMemberList(searchDto, 1);

        list.forEach(System.out::println);
        System.out.println(list.getContent());

        Assertions.assertEquals(list.getTotalElements(), 1);
    }

    @Test
    public void 관리자_사원검색_상태() {
        MemberSearchDto searchDto = MemberSearchDto
                .builder()
                .status(MemberStatus.IN_USE)
                .build();

        Page<MemberDto> list = memberService.getSearchMemberList(searchDto, 1);

        list.forEach(System.out::println);
        System.out.println(list.getContent());

        Assertions.assertEquals(list.getTotalElements(), 2);
    }

    @Test
    public void 관리자_사원정보변경() {
        MemberUpdateDto memberUpdateDto = MemberUpdateDto
                .builder()
                .memberName("홍길동")
                .memberCellphone("010-7777-6666")
                .memberEmail("sfdf@daum.net")
                .annualCount(16)
                .modifiedBy("madmin")
                .modifiedDate(LocalDateTime.now())
                .retireDate(LocalDateTime.now())
                .memberAuth(RoleType.MEMBER)
                .memberStatus(MemberStatus.IN_USE)
                .build();

        memberService.modifyMember("id", memberUpdateDto);

        Page<MemberDto> list = memberService.getAllMemberList(1);
        list.stream().forEach(System.out::println);
    }

    @Test
    public void 관리자_사원상세정보조회() {
        Optional<Member> member = memberRepository.findByMemberId("id");

        MemberDto memberDto = MemberDto.builder()
                .memberId(member.get().getMemberId())
                .memberName(member.get().getMemberInfo().getName())
                .departmentName(member.get().getDepartment().getName())
                .memberCellphone(member.get().getMemberInfo().getCellphone())
                .memberEmail(member.get().getMemberInfo().getEmail())
                .createdDate(member.get().getCreatedDate())
                .retireDate(member.get().getRetireDate())
                .memberStatus(member.get().getMemberStatus())
                .build();

        System.out.println(memberDto);
    }
    
}