package tko.refresh.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tko.refresh.domain.emb.Period;
import tko.refresh.util.converter.DateConverter;
import tko.refresh.util.page.GlobalPage;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Getter
@ToString
public class AnnualManageDto implements GlobalPage {
    private final String FORMAT = "YYYY년 MM월 dd일";

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

    private String startDate;

    private String endDate;


    @NotEmpty
    private String createdDate;


    @Builder
    public AnnualManageDto(String annualUid, String memberName, String departmentName, String annualType, String annualStatus, Period period, LocalDateTime createdDate) {
        this.annualUid = annualUid;
        this.memberName = memberName;
        this.departmentName = departmentName;
        this.annualType = annualType;
        this.annualStatus = annualStatus;
        this.startDate = DateConverter.format(period.getStartDate(),FORMAT);
        this.endDate = DateConverter.format(period.getEndDate(),FORMAT);
        this.createdDate = DateConverter.format(createdDate,FORMAT);
    }
}
