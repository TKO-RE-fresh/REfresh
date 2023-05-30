package tko.refresh.dto.calendar.request.calendar;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.dto.YearMonthDto;
import tko.refresh.util.valid.ValidDeptName;

@Getter
public class CalendarReqDto {
    @Valid
    @NotNull
    private final YearMonthDto yearMonth;

    @ValidDeptName
    private final String deptName;

    @Builder
    public CalendarReqDto(int month, int year, String deptName) {
        this.yearMonth = YearMonthDto.builder().month(month).year(year).build();
        this.deptName = deptName;
    }


}
