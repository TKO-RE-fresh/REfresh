package tko.refresh.dto.calendar.request.annual;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tko.refresh.dto.TodayDto;
import tko.refresh.util.page.GlobalPage;
import tko.refresh.util.valid.ValidDate;
import tko.refresh.util.valid.ValidDeptName;

@Getter @ToString
public class GetAnnualByDateAndDeptReqDto implements GlobalPage {
    @ValidDate
    private final TodayDto today;
    @NotBlank
    @ValidDeptName
    private final String deptName;

    @NotNull
    private final Pageable pageable;

    @Builder
    public GetAnnualByDateAndDeptReqDto(TodayDto today, String deptName, @Min(1) int page, @Min(1)int size) {
        this.today = today;
        this.deptName = deptName;
        this.pageable = setPageable(page, size);
    }

}
