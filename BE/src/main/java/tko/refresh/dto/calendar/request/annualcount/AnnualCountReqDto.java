package tko.refresh.dto.calendar.request.annualcount;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.dto.YearMonthDto;
import tko.refresh.util.valid.ValidDeptName;
@Builder @Getter
public class AnnualCountReqDto {

    @ValidDeptName
    private final String deptName;

    @Valid
    @NotNull
    private final YearMonthDto yearMonth;

}
