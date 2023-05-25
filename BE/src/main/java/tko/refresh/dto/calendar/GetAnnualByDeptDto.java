package tko.refresh.dto.calendar;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder @ToString @Getter
public class GetAnnualByDeptDto {
    private final String dept;
    private final int year;
    private final int month;
}
