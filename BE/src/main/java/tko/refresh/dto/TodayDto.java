package tko.refresh.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TodayDto {
    @Min(1900)
    @Max(2100)
    private final int year;

    private final int month;

    private final int day;

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(year, month, day, 0, 0);
    }

}
