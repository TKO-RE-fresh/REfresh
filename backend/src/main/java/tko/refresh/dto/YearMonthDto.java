package tko.refresh.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder @Getter @ToString
public class YearMonthDto {

    @Max(2100)
    @Min(1900)
    private final int year;

    @Max(12)
    @Min(1)
    private final int month;
}
