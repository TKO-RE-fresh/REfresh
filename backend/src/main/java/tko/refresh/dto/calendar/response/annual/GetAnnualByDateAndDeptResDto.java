package tko.refresh.dto.calendar.response.annual;

<<<<<<< HEAD
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.domain.emb.MemberInfo;
=======
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.util.valid.ValidDeptName;

@Getter
public class GetAnnualByDateAndDeptResDto extends AnnualResponse {

<<<<<<< HEAD
    @NotNull
    private MemberInfo memberInfo;


    @Builder
    public GetAnnualByDateAndDeptResDto(MemberInfo memberInfo, String annualType, Period period) {
        super(annualType, period.getStartDate().toLocalDate(), period.getEndDate().toLocalDate());
        this.memberInfo = memberInfo;
=======
    @NotBlank
    private String memberName;

    @NotBlank
    @ValidDeptName
    private String deptName;

    @Builder
    public GetAnnualByDateAndDeptResDto(String memberName, String deptName, AnnualType annualType, Period period) {
        super(annualType, period);
        this.deptName = deptName;
        this.memberName = memberName;
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
    }


}
