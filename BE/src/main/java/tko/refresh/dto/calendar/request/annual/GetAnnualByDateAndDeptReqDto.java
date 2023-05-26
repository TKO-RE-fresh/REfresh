package tko.refresh.dto.calendar.request.annual;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.dto.calendar.TodayDto;
import tko.refresh.util.valid.ValidDate;
import tko.refresh.util.valid.ValidDeptName;

@Getter
public class GetAnnualByDateAndDeptReqDto implements GlobalPage {
    @ValidDate
    private final TodayDto today;
    @NotBlank
    @ValidDeptName
    private final String deptName;

    @NotNull
    private final Pageable pageable;

    @Builder
    public GetAnnualByDateAndDeptReqDto(TodayDto today, String deptName, int page, int size) {
        this.today = today;
        this.deptName = deptName;
        this.pageable = setPageable(page, size);
    }

}
