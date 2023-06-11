package tko.refresh.dto.calendar.response.annual;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.emb.Period;

@Getter
public class GetAnnualByDateAndDeptResDto extends AnnualResponse {

    @NotNull
    private MemberInfo memberInfo;


    @Builder
    public GetAnnualByDateAndDeptResDto(MemberInfo memberInfo, String annualType, Period period) {
        super(annualType, period.getStartDate().toLocalDate(), period.getEndDate().toLocalDate());
        this.memberInfo = memberInfo;
    }


}
