package tko.refresh.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.domain.enu.AnnualType;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class AnnualManageDto {

    @NotEmpty
    private String annualUid;
    @NotEmpty
    private String memberName;
    @NotEmpty
    private String departmentName;
    @NotEmpty
    private String annualType;

    @NotEmpty
    private String annualStatus;

    private Period period;

    @NotEmpty
    private String createdDate;

}
