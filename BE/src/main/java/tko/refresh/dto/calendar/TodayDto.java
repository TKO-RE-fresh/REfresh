package tko.refresh.dto.calendar;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TodayDto {
    @Min(1900)
    @Max(2100)
    private final int year;
    @Min(1)
    @Max(12)
    private final int month;
    @Min(1)
    @Max(31)
    private final int day;

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(year, month, day, 0, 0);
    }

}
