package tko.refresh.dto.leaveRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tko.refresh.domain.emb.Period;
import tko.refresh.util.valid.ValidPeriod;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Getter
@ToString
public class LeaveRequestDto {
    @NotBlank
    private String memberId;

    @NotBlank
    private String annualType;

//    @NotBlank
//    private LocalDateTime startDate;
//
//    @NotBlank
//    private LocalDateTime endDate;
    @Valid
    @ValidPeriod
    private Period period;

    public LeaveRequestDto() {

    }

    public LeaveRequestDto(String memberId, String annualType, LocalDateTime startDate, LocalDateTime endDate) {
        this.memberId = memberId;
        this.annualType = annualType;
        this.period = Period.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

    }




}
