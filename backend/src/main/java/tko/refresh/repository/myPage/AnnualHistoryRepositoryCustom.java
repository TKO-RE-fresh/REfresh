package tko.refresh.repository.myPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tko.refresh.dto.myPage.AnnualHistoryDto;
import tko.refresh.dto.myPage.HistorySearchDto;

public interface AnnualHistoryRepositoryCustom {

    Page<AnnualHistoryDto> getAnnualHistoryPage(HistorySearchDto searchDto, Pageable pageable);
}
