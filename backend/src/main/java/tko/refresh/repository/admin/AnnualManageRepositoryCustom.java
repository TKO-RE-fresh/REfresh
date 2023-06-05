package tko.refresh.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tko.refresh.domain.entity.Annual;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.dto.admin.AnnualSearchDto;

import java.util.List;


public interface AnnualManageRepositoryCustom {

    Page<AnnualManageDto> searchAnnual(AnnualSearchDto searchDto, Pageable pageable);

}
