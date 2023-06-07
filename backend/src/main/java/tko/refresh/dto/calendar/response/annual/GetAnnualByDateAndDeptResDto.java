package tko.refresh.dto.calendar.response.annual;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.util.valid.ValidDeptName;

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
