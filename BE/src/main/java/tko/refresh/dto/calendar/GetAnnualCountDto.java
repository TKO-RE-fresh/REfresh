package tko.refresh.dto.calendar;

import java.time.LocalDateTime;

import lombok.Builder;

public class GetAnnualCountDto {
    private LocalDateTime annualDate;
    private int sumCount;

    @Builder
    public GetAnnualCountDto(LocalDateTime annualDate, int sumCount) {
        this.annualDate = annualDate;
        this.sumCount = sumCount;
    }
}
