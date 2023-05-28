package tko.refresh.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.domain.entity.Annual;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.dto.admin.AnnualSearchDto;
import tko.refresh.repository.admin.annualManage.AnnualManageRepository;
import tko.refresh.util.page.Pagination;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnualManageService {
    private final int PAGE_SIZE = 10;

    private final AnnualManageRepository annualManageRepo;

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

}
