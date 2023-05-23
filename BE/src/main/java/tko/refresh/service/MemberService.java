package tko.refresh.service;

import static java.time.LocalDateTime.*;
import static tko.refresh.domain.value.enu.AnnualStatus.*;
import static tko.refresh.domain.value.enu.AnnualType.*;
import static tko.refresh.domain.value.enu.MemberStatus.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import tko.refresh.domain.Annual;
import tko.refresh.domain.AnnualCount;
import tko.refresh.domain.Department;
import tko.refresh.domain.Member;
import tko.refresh.domain.value.enu.AnnualStatus;
import tko.refresh.domain.value.enu.AnnualType;
import tko.refresh.domain.value.enu.MemberStatus;
import tko.refresh.repository.AnnualCountRepository;
import tko.refresh.repository.AnnualRepository;
import tko.refresh.repository.DepartmentRepository;
import tko.refresh.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    private final AnnualCountRepository annualCountRepository;

    private final AnnualRepository annualRepository;

    public void save() {

        Department d = Department.builder().name("영업팀")
                                 .createdDate(now()).modifiedDate(now()).modifiedBy("admin").createdDate(now()).createdBy("admin")
                .manager("admin")
                                 .build();

        Member member = Member.builder()
                              .name("김다니엘")
                .modifiedDate(now()).modifiedBy("admin").createdDate(now()).createdBy("admin")
                .annualCount(14.5)
                .id("minsung")
                .email("gmail@com")
                .password("1234")
                .cellphone("010-2345-2423")
                .memberStatus(IN_USE)
                              .department(d).build();
        d.getMember().add(member);

        departmentRepository.save(d);
        memberRepository.save(member);
        annualRepository.save(Annual.builder().member(member).createdDate(now()).modifiedDate(now()).modifiedBy("admin").createdBy("admin")
                                    .annualType(ANNUAL_LEAVE)
                                      .annualStatus(WAITING)
                                    .build());
        annualCountRepository.save(AnnualCount.builder()
                                              .annualDate(LocalDateTime.now())
                                           .sumCount(1)
                                           .department(d)
                                           .build());


    }
}
