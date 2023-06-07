package tko.refresh.dto.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.util.converter.DateConverter;
import tko.refresh.util.page.GlobalPage;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class AnnualManageDto {

    @NotEmpty
    private String annualUid;
    @NotEmpty
    private String memberName;

    private String email;
    @NotEmpty
    private String departmentName;
    @NotEmpty
    private String annualType;

    @NotEmpty
    private String annualStatus;

    private String startDate;

    private String endDate;

    @NotEmpty
    private String createdDate;

    private String rejectReason;


    @Builder
    @QueryProjection
    public AnnualManageDto(UUID annualUid, String memberName, String email, String departmentName, AnnualType annualType,
                           AnnualStatus annualStatus, Period period, LocalDateTime createdDate, String rejectReason) {
        String format = "YYYY년 MM월 dd일";
        this.annualUid = annualUid.toString();
        this.memberName = memberName;
        this.departmentName = departmentName;
        this.email = email;
        this.annualType = annualType.getCode();
        this.annualStatus = annualStatus.getLabel();
        this.startDate = DateConverter.format(period.getStartDate(),format);
        this.endDate = DateConverter.format(period.getEndDate(),format);
        this.createdDate = DateConverter.format(createdDate,format);
        this.rejectReason = rejectReason;
    }
}
