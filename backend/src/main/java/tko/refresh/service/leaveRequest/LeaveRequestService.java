package tko.refresh.service.leaveRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.dto.leaveRequest.LeaveRequestDto;
import tko.refresh.repository.leaveRequest.LeaveRequestRepository;
import tko.refresh.repository.leaveRequest.LeaveRequesterInfoRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.service.admin.AnnualManageService;

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

    /* 사용한 연차일 수 받아오기 */
    public Map<String, Double> getUsedAnnualLeave(String memberId) {
        double annualCount = leaveRequesterInfoRepository.findByAnnualCount(memberId);
        double usedAnnualCount = 15 - annualCount;
        Map<String, Double> annualLeaveMap = new HashMap<>();
        annualLeaveMap.put("annualCount", annualCount);
        annualLeaveMap.put("usedAnnualCount", usedAnnualCount);
        return annualLeaveMap;
    }
//
//    public boolean filterHoliday(){
//
//        Period period = Period.builder().startDate(leaveRequestDto.getStartDate()).endDate(leaveRequestDto.getEndDate()).build();
//        int notholidays = annualManageService.WorkingDaysCounter(period);
//        // 휴가기간중 공휴일 제외 (메서드명 바꿔야함을 강력 추천)
//
//        leaveRequestDto.setAnnualCount(notholidays);
//
//
//        System.out.println("공휴이 아닌 휴가일수: " + notholidays);
//    }


    private final LeaveRequestRepository leaveRequestRepository;


    /* 클라이언트로부터 들어온 정보를 토대로 연차를 신청 */
    @Transactional
    public boolean createLeaveRequest(LeaveRequestDto leaveRequestDto) {
        boolean result = true;
        System.out.println("멤버 이름 확인: "+memberRepository.findByMemberId("jaeseok").get());
        System.out.println("멤버 이름 확인 DTO: "+leaveRequestDto.getMemberId());
//        Member member = memberRepository.findByMemberId(leaveRequestDto.getMemberId()).get();
        Optional<Member> member = memberRepository.findByMemberId(leaveRequestDto.getMemberId());

        /* 멤버 객체 존재여부 확인 */
        if(!member.isPresent()) return false;

        /* 멤버의 이름 및 연차 타입이 null인지 확인 */
        if (leaveRequestDto.getMemberName() == null || leaveRequestDto.getAnnualType() == null) {
            return false;
        }


        System.out.println("멤버 확인: " + member);
        Annual annual = Annual
                .builder()
                .member(member.get())
                .createdBy(member.get().getMemberInfo().getName())
                .modifiedBy(member.get().getMemberInfo().getName())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .annualType(AnnualType.valueOf(leaveRequestDto.getAnnualType()))
                .period(Period
                        .builder()
                        .startDate(leaveRequestDto.getStartDate())
                        .endDate(leaveRequestDto.getEndDate())
                        .build())
                .build();


        System.out.println("annual 정보: "+annual);

//        member.addAnnual();
        leaveRequestRepository.save(annual);

        return result;
    }
}