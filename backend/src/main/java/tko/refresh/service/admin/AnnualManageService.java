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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnualManageService {
    private final int PAGE_SIZE = 5;

    private final AnnualManageRepository annualManageRepo;
    private final MemberRepository memberRepository;
    private final HolidayRepository holidayRepository;
    private final AnnualCountRepository annualCountRepository;

    public List<AnnualManageDto> getAnnualManageAllList(int page){
        Pageable pageable = Pagination.setPageable(page,PAGE_SIZE);
        List<Annual> list = annualManageRepo.findAllWithMember(pageable);
        return entityToDto(list);
    }

    public Page<AnnualManageDto> getSearchAnnualMangeList(AnnualSearchDto searchDto,int page){
        Pageable pageable = Pagination.setPageable(page,PAGE_SIZE);
        Page<Annual> list = annualManageRepo.searchAnnual(searchDto,pageable);
        return entityToDto(list);
    }

    @Transactional
    public boolean AccessAnnualRequest(UUID uid){
        Annual annual = annualManageRepo.findByOne(uid).orElse(null);
        if(annual == null) return false;
        Period period = annual.getPeriod();
        Member member = annual.getMember();
        int sumResult = 0,discountResult=0 ,statusResult =0;
        // 연차는 계산한 값, 반차는 0.5
        double discount= annual.getAnnualType().equals(AnnualType.ANNUAL_LEAVE) ? WorkingDaysCounter(period) : 0.5 ;

        if(discount < 0) return false;

        discountResult = memberRepository.discountAnnualCount(member.getMemberId(), discount);
        sumResult = annualCountRepository.setAnnualSumCount(period.getStartDate(), period.getEndDate(), member.getDepartment().getUid());
        //승인 상태 변경
        statusResult = annualManageRepo.acceptAnnualStatus(uid,AnnualStatus.AGREE,"admin");
        return successTransaction(discountResult, sumResult, statusResult);
    }

    // 연차 차감, 연차 집계, 상태 변경이 ALL VALID CHECK FUNCTION
    private boolean successTransaction(int ...args){
        return Arrays.stream(args).allMatch(e -> e > 0);
    };


    @Transactional
    public boolean RejectAnnualRequest(UUID uid,String msg){
        Annual annual = annualManageRepo.findById(uid).orElse(null);
        if(annual == null) return false;
        Period period = annual.getPeriod();
        Member member = annual.getMember();
        return annualManageRepo.rejectAnnualStatus(uid, AnnualStatus.REJECT, "admin",msg) > 0;
    }


//    public Page<AnnualManageDto> entityToDto(Page<Annual> list){
//        List<AnnualManageDto> resultList = new ArrayList<>();
//
//        for(Annual data : list ){
//            resultList.add(AnnualManageDto.builder()
//                    .annualUid(String.valueOf(data.getUid()))
//                    .memberName(data.getMember().getMemberInfo().getName())
//                    .email(data.getMember().getMemberInfo().getEmail())
//                    .departmentName(data.getMember().getDepartment().getName())
//                    .annualType(data.getAnnualType())
//                    .annualStatus(data.getAnnualStatus())
//                    .period(data.getPeriod())
//                    .createdDate(data.getCreatedDate())
//                    .build()
//            );
//        }
//        return resultList;
//    }


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
