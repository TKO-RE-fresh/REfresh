package tko.refresh.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tko.refresh.domain.entity.Annual;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.repository.admin.AnnualManageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnualManageService {

    private final AnnualManageRepository annualManageRepo;

    public List<AnnualManageDto> getAnnualManageAllList(){
        List<AnnualManageDto> resultList = new ArrayList<>();
        List<Annual> annualList = annualManageRepo.findAllWithMember();

        for(Annual data : annualList ){
            resultList.add(AnnualManageDto.builder()
                            .annualUid(String.valueOf(data.getUid()))
                            .memberName(data.getMember().getMemberInfo().getName())
                            .departmentName(data.getMember().getDepartment().getName())
                            .annualType(data.getAnnualType().getCode())
                            .annualStatus(data.getAnnualStatus().getLabel())
                            .startDate(data.getPeriod().getStartDate())
                            .endDate(data.getPeriod().getEndDate())
                            .createdDate(data.getCreatedDate())
                    .build()
            );
        }
        return resultList;
    }
}
