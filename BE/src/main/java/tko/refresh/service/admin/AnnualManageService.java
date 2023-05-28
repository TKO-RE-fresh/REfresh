package tko.refresh.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.domain.entity.Annual;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.repository.admin.AnnualManageRepository;
import tko.refresh.util.page.Pagination;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnualManageService {
    private final int PAGE_SIZE = 10;

    private final AnnualManageRepository annualManageRepo;

    public List<AnnualManageDto> getAnnualManageAllList(int page){
        List<AnnualManageDto> resultList = new ArrayList<>();
        Pageable pageable = Pagination.setPageable(page,PAGE_SIZE);
        List<Annual> annualList = annualManageRepo.findAllWithMember(pageable);

        for(Annual data : annualList ){
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
}
