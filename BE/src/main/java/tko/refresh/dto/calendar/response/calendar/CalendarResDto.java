package tko.refresh.dto.calendar.response.calendar;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class CalendarResDto {
    private final int day;
    private final String hoName;

    private final int sumCount;

}
