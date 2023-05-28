package tko.refresh.dto.calendar.response.calendar;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@Builder
public class CalendarResDto  {
    private final int day;
    private final String hoName;

    private final int sumCount;


}
