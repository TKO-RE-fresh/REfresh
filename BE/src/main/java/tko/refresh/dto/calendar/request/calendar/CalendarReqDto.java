package tko.refresh.dto.calendar.request.calendar;

import javax.validation.Valid;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.dto.calendar.YearMonthDto;
import tko.refresh.util.valid.ValidDeptName;

@Builder @Getter
public class CalendarReqDto {
    @Valid
    private final YearMonthDto yearMonth;

    @ValidDeptName
    private final String deptName;
}
