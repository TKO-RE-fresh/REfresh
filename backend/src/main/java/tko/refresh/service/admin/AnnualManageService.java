package tko.refresh.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.dto.admin.AnnualSearchDto;
import tko.refresh.repository.admin.AnnualManageRepository;
import tko.refresh.repository.calendar.AnnualCountRepository;
import tko.refresh.repository.calendar.HolidayRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.util.page.Pagination;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnualManageService {
    private final int PAGE_SIZE = 1;

    private final AnnualManageRepository annualManageRepo;
    private final MemberRepository memberRepository;
    private  final HolidayRepository holidayRepository;
    private final AnnualCountRepository annualCountRepository;

    public Page<AnnualManageDto> getSearchAnnualMangeList(AnnualSearchDto searchDto,int page){
        Pageable pageable = Pagination.setPageable(page,PAGE_SIZE);
        Page<AnnualManageDto> list = annualManageRepo.searchAnnual(searchDto,pageable);
        return list;
    }

    @Transactional
    public boolean AccessAnnualRequest(UUID uid){
        Annual annual = annualManageRepo.findByOne(uid).orElse(null);
        if(annual == null) return false;
        Period period = annual.getPeriod();
        Member member = annual.getMember();
        int sumResult = 0, annualCountResult=0 ,statusResult =0;
        // 연차는 계산한 값, 반차는 0.5
        double periodCount= annual.getAnnualType().equals(AnnualType.ANNUAL_LEAVE) ? WorkingDaysCounter(period) : 0.5 ;

        if(periodCount < 0 && member.getAnnualCount() - periodCount < 1) return false;

        //사원 연차 차감
        annualCountResult = memberRepository.setSubAnnualCount(member.getMemberId(), periodCount);
        //연차 집계 증가
        sumResult = annualCountRepository.setAnnualSumCount(period.getStartDate(), period.getEndDate(), member.getDepartment().getUid());
        //승인 상태 변경
        statusResult = annualManageRepo.acceptAnnualStatus(uid,AnnualStatus.AGREE,"admin");

        return successTransaction(annualCountResult, sumResult, statusResult);
    }

    // 연차 차감, 연차 집계, 상태 변경이 ALL VALID CHECK FUNCTION
    public boolean successTransaction(int ...args){
        return Arrays.stream(args).allMatch(e -> e > 0);
    };


    @Transactional(rollbackOn = RuntimeException.class)
    public boolean RejectAnnualRequest(UUID uid,String msg){
        Annual annual = annualManageRepo.findById(uid).orElse(null);
        if(annual == null) return false;

        boolean check = annualManageRepo.rejectAnnualStatus(uid, AnnualStatus.REJECT, "admin",msg) > 0;
        if(!check) throw new RuntimeException("연차 반려 처리를 실패 했습니다.");

        return check;
    }

    public int WorkingDaysCounter(Period period){
        int workingDays=0;
        LocalDateTime date = period.getStartDate();

        // 주말 빼고 계산
        while(date.isBefore(period.getEndDate()) || date.isEqual(period.getEndDate())){
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) workingDays++;
            date=date.plusDays(1);
        }
        // 공휴일 빼고 계산
        workingDays -= holidayRepository.countByDateBetween(period.getStartDate().toLocalDate(),period.getEndDate().toLocalDate());
        return workingDays;
    }
}
