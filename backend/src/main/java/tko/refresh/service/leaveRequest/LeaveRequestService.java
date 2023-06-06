package tko.refresh.service.leaveRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.dto.leaveRequest.LeaveRequestDto;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.dto.GlobalResponseDto;
import tko.refresh.repository.calendar.AnnualRepository;
import tko.refresh.repository.leaveRequest.LeaveRequestRepository;
import tko.refresh.repository.leaveRequest.LeaveRequesterInfoRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.service.admin.AnnualManageService;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {
    private final LeaveRequesterInfoRepository leaveRequesterInfoRepository;
    private final MemberRepository memberRepository;
    private final AnnualManageService annualManageService;
    private final AnnualRepository annualRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    /* 사용한 연차일 수 받아오기 */
    public Map<String, Double> getUsedAnnualLeave(String memberId) {
        double restLeaveCount = leaveRequesterInfoRepository.findByAnnualCount(memberId);
        double usedLeaveCount = 15 - restLeaveCount;
        Map<String, Double> annualLeaveMap = new HashMap<>();
        annualLeaveMap.put("restLeaveCount", restLeaveCount);
        annualLeaveMap.put("usedLeaveCount", usedLeaveCount);
        return annualLeaveMap;
    }

    /* 클라이언트로부터 들어온 정보를 토대로 연차를 신청 */
    @Transactional
    public GlobalResponseDto createLeaveRequest(LeaveRequestDto dto) {
        System.out.println("createleaverequest의 dto 확인: " + dto);

        // 이름을 가지고 Member 객체를 찾는다.
        Optional<Member> member = memberRepository.findByMemberId(dto.getMemberId());
        System.out.println("멤버 확인 tq: " + member);
        System.out.println("멤버 존재여부 확인: " + member.isPresent());

        /* 멤버 객체 존재여부 확인 */
        if(!member.isPresent()) {
            return GlobalResponseDto.builder()
                    .statusCode(HttpServletResponse.SC_BAD_REQUEST)
                    .message("존재하지 않는 멤버입니다.")
                    .build();
        }
        double workday = WorkdayCalReq(member.get().getAnnualCount(), dto);

        System.out.println("workday 확인: "+ workday);

        /* 연차 개수보다 더 많은 연차 신청 시 */
        if(workday < 0) {
            return GlobalResponseDto.builder()
                    .statusCode(HttpServletResponse.SC_BAD_REQUEST)
                    .message("연차 개수를 초과하였습니다.")
                    .build();
        }

        Member existMember = member.get();

        System.out.println("exist member check: "+ existMember.getMemberInfo());
        System.out.println("exist member chekc: " + existMember.getMemberId());
        System.out.println("exist member chekc: " + existMember.getAnnualCount());
        System.out.println("서비스 시작일 확인: " +dto.getPeriod().getStartDate());
        System.out.println("서비스 종료일 확인: " +dto.getPeriod().getEndDate());

        /* 연차 신청하려는 날짜가 이미 신청된 날짜인지 확인 */
        int count = annualRepository.countAnnualByMemberUidAndPeriod(existMember.getMemberId(),
                                                                 dto.getPeriod().getStartDate(),
                                                                 dto.getPeriod().getEndDate());

//        System.out.println("카운트 확인: " + count);

        if(count > 0) {
            return GlobalResponseDto.builder()
                    .statusCode(HttpServletResponse.SC_BAD_REQUEST)
                    .message("이미 신청된 날짜 범위에 해당합니다.")
                    .build();
        }

//        System.out.println("메이크에뉴얼바이멤버: "+ makeAnnualByMember(existMember, dto));

        Annual save = leaveRequestRepository.save(makeAnnualByMember(existMember, dto));
//        System.out.println("세이브: "+ save);
        existMember.addAnnual(save);
        save.setMember(existMember);

        return GlobalResponseDto.builder()
                .statusCode(HttpServletResponse.SC_OK)
                .message("연차 신청이 완료되었습니다.")
                .build();
    }


    private Annual makeAnnualByMember(Member member, LeaveRequestDto dto) {
//        System.out.println("멤버 확인 ㅋㅋㅋㅋㅋ: "+ member);
        System.out.println("디티오 확인 ㅋㅋㅋ  : " + dto);
        System.out.println("지금 시간 확인" + LocalDateTime.now());
        return Annual
                .builder()
                .member(member)
                .createdBy(member.getMemberId())
                .modifiedBy(member.getMemberId())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .annualStatus(AnnualStatus.WAITING)
                .annualType(AnnualType.getEnumByCode(dto.getAnnualType()))
                .period(Period.builder().startDate(dto.getPeriod().getStartDate())
                              .endDate(dto.getPeriod().getEndDate())
                                .build()).build();
    }

    private double WorkdayCalReq(double count, LeaveRequestDto dto) {
        return count - annualManageService.WorkingDaysCounter(dto.getPeriod());
    }
}