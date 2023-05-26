package tko.refresh.dto.calendar.response.annualcount;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class AnnualCountResDto {
    private final LocalDate annualDate;
    private final int sumCount;
}
