package tko.refresh.dto.calendar;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder @ToString @Getter
public class GetAnnualByNameFilterDto {

    private final String name;
    private final int page;
    private final int size;

}
