package tko.refresh.repository.leaveRequest;

import org.assertj.core.api.Assertions;
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
import tko.refresh.repository.calendar.DepartmentRepository;
import tko.refresh.repository.member.MemberRepository;
import java.time.LocalDateTime;


@SpringBootTest
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
class LeaveRequestRepositoryTest {
//
//    @Autowired
//    private DepartmentRepository departmentRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @BeforeEach  // 테스트 메서드전 실행
//    void setup(){
//        Department department = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(),
//                LocalDateTime.now());
//        MemberInfo memberInfo = new MemberInfo("name1245", "012-1234-2124", "position@gmail.com");
//        Member member = new Member("id", "1234", memberInfo, 15, MemberStatus.IN_USE, RoleType.MEMBER , department,
//                LocalDateTime.now(), LocalDateTime.now(), null, "dds", "sdds");
//        department.addMember(member);
//        member.setDepartment(department);  // department가 기본적으로 Null이라
//
//        departmentRepository.save(department);
//        memberRepository.save(member);
//    }
//
//    @Autowired
//    private LeaveRequestRepository leaveRequestRepository;
//
//    @Test
//    void 연차집계테스트(){
//        Double id = leaveRequestRepository.findByAnnualCount("id");
//        Assertions.assertThat(id).isEqualTo(15);
//    }
}