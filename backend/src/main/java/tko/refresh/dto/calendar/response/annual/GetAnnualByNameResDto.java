package tko.refresh.dto.calendar.response.annual;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualType;
@Getter
public class GetAnnualByNameResDto extends AnnualResponse {
    @NotNull
    private MemberInfo memberInfo;

    @Builder
    public GetAnnualByNameResDto(String annualType, Period period, MemberInfo memberInfo) {
        super(annualType, period.getStartDate().toLocalDate(), period.getEndDate().toLocalDate());
        this.memberInfo = memberInfo;
    }
}
