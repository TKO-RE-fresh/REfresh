package tko.refresh.dto.calendar.response;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.dto.calendar.request.valid.ValidPeriod;

@Builder
public class GetAnnualByNameResDto {
    @NotNull
    private final MemberInfo memberInfo;
    @NotNull
    private final AnnualType annualType;

    @NotNull
    @ValidPeriod
    private final Period period;


}
