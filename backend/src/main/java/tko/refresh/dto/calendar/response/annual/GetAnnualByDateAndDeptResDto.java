package tko.refresh.dto.calendar.response.annual;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.util.valid.ValidDeptName;

@Getter
public class GetAnnualByDateAndDeptResDto extends AnnualResponse {

    @NotBlank
    private String memberName;

    @NotBlank
    @ValidDeptName
    private String deptName;

    @Builder
    public GetAnnualByDateAndDeptResDto(String memberName, String deptName, String annualType, Period period) {
        super(annualType, period.getStartDate().toLocalDate(), period.getEndDate().toLocalDate());
        this.deptName = deptName;
        this.memberName = memberName;
    }


}
