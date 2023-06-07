package tko.refresh.dto.myPage;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.util.converter.DateConverter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class AnnualHistoryDto {
    private final String formatDateTime = "YYYY년 MM월 dd일 hh:mm";
    private final String formatDate = "YYYY년 MM월 dd일";

    private String annualUid;

    private String annualType;

    private String annualStatus;

    private String startDate;

    private String endDate;

    private String createdDate;

    private String acceptorName;

    private String acceptorEmail;

    private String modifiedDate;

    private String rejectReason;


    @Builder
    @QueryProjection
    public AnnualHistoryDto(UUID annualUid, AnnualType annualType, AnnualStatus annualStatus, String acceptorName, String acceptorEmail,
                            Period period, LocalDateTime createdDate, LocalDateTime modifiedDate , String rejectReason) {
    this.annualUid = annualUid.toString();
    this.annualType = annualType.getCode();
    this.annualStatus = annualStatus.getLabel();
    this.startDate = DateConverter.format(period.getStartDate(),formatDate);
    this.endDate = DateConverter.format(period.getEndDate(),formatDate);
    this.createdDate = DateConverter.format(createdDate,formatDateTime);
    this.acceptorName = acceptorName;
    this.acceptorEmail = acceptorEmail;
    this.modifiedDate =  DateConverter.format(modifiedDate,formatDate);
    this.rejectReason = rejectReason;
    }

}
