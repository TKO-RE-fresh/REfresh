package tko.refresh.repository.admin.annualManage;

import org.springframework.data.domain.Pageable;
import tko.refresh.domain.entity.Annual;
import tko.refresh.dto.admin.AnnualSearchDto;

import java.util.List;


public interface AnnualManageRepositoryCustom {

    List<Annual> searchAnnual(AnnualSearchDto searchDto, Pageable pageable);
}
