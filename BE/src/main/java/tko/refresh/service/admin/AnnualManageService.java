package tko.refresh.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.Member;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.dto.admin.AnnualSearchDto;
import tko.refresh.repository.admin.annualManage.AnnualManageRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.util.page.Pagination;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnualManageService {
    private final int PAGE_SIZE = 10;

    private final AnnualManageRepository annualManageRepo;
    private final MemberRepository memberRepository;

    public List<AnnualManageDto> getAnnualManageAllList(int page){
        Pageable pageable = Pagination.setPageable(page,PAGE_SIZE);
        List<Annual> list = annualManageRepo.findAllWithMember(pageable);


        return entityToDto(list);
    }

    public List<AnnualManageDto> getSearchAnnualMangeList(AnnualSearchDto searchDto,int page){
        Pageable pageable = Pagination.setPageable(page,PAGE_SIZE);
        List<Annual> list = annualManageRepo.searchAnnual(searchDto,pageable);
        return entityToDto(list);
    }

    @Transactional
    public boolean processAnnualRequest(String uid, String status){
        boolean result =  true;
        UUID annualUid = UUID.fromString(uid);
        Optional<Annual> annual = annualManageRepo.findById(annualUid);
        if(annual.isEmpty()) return false;
        Optional<Member> member = memberRepository.findById(annual.get().getMember().getUid());
        if(member.isEmpty()) return false;

        int annualResult = annualManageRepo.updateAnnualStatus(annualUid,status,"admin");




        return  true;
    }


    public List<AnnualManageDto> entityToDto(List<Annual> list){
        List<AnnualManageDto> resultList = new ArrayList<>();

        for(Annual data : list ){
            resultList.add(AnnualManageDto.builder()
                    .annualUid(String.valueOf(data.getUid()))
                    .memberName(data.getMember().getMemberInfo().getName())
                    .departmentName(data.getMember().getDepartment().getName())
                    .annualType(data.getAnnualType().getCode())
                    .annualStatus(data.getAnnualStatus().getLabel())
                    .period(data.getPeriod())
                    .createdDate(data.getCreatedDate())
                    .build()
            );
        }
        return resultList;
    }



    public double WorkingDaysCounter(Period period){
        int workingDays=0;
        LocalDateTime date = period.getStartDate();

        // 주말 빼고 계산
        while(date.isBefore(period.getEndDate())){
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) workingDays++;
            date=date.plusDays(1);
        }
        // start~end 계산시 count 빼기 만약에 음수(-)가 나오면 반려 처리


        return workingDays;
    }
}
