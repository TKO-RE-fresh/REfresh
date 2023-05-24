package tko.refresh.dto.calendar;

import lombok.Builder;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualType;

@Builder
public class GetAnnualSearchDto {
    private final MemberInfo memberInfo;
    private final AnnualType annualType;

    private final Period period;
}
