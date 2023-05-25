package tko.refresh.dto.calendar.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import tko.refresh.domain.emb.Period;
import tko.refresh.dto.calendar.request.valid.ValidDeptName;
import tko.refresh.dto.calendar.request.valid.ValidPeriod;

@Builder
public class GetAnnualByDateAndDeptResDto {

    @NotBlank
    private final String memberName;
    @NotBlank
    @ValidDeptName
    private final String deptName;
    @NotBlank
    private final String annualType;

    @NotNull
    @ValidPeriod
    private final Period period;


}
