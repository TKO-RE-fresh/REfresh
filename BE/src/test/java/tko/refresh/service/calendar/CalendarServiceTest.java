package tko.refresh.service.calendar;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tko.refresh.domain.entity.Holiday;
import tko.refresh.dto.YearMonthDto;
import tko.refresh.dto.calendar.request.calendar.CalendarReqDto;
import tko.refresh.dto.calendar.response.calendar.CalendarResDto;
import tko.refresh.repository.calendar.AnnualCountEmRepository;
@SpringBootTest
class CalendarServiceTest {
    @Autowired
    private AnnualCountEmRepository emr;
    @Autowired
    private HolidayService hs;

    private CalendarService calendarService;

    @BeforeEach
    void setUp() throws Exception {
        calendarService = new CalendarService(emr, hs);
    }


    @Test
    void 달력_생성_테스트() {
        CalendarReqDto dto = CalendarReqDto.builder().yearMonth(YearMonthDto.builder().year(2023)
                                                                                   .month(5).build()).deptName(
                "개발팀").build();
        List<CalendarResDto> calendarRes = calendarService.updateCalendar(dto);
        assertThat(calendarRes.size()).isEqualTo(35);
    }

    @Test
    void 윤년_테스트() {
        // 4로 나누어 떨어지지않는 해
        boolean leapYear = calendarService.getLeapYear(2023);
        assertThat(leapYear).isEqualTo(false);

        // 4로 나누어 떨어지지만 100으로 나누어 떨어지는 해는 평년
        leapYear = calendarService.getLeapYear(2100);
        assertThat(leapYear).isEqualTo(false);

        // 4로 나누어 떨어지고 100으로 나누어 떨어지지만 400으로 나누어 떨어지는 해는 윤년
        leapYear = calendarService.getLeapYear(2000);
        assertThat(leapYear).isEqualTo(true);

        // 4로 나누어떨어지지만 100으로 나누어 떨어지지 않는 해는 윤년
        leapYear = calendarService.getLeapYear(2024);
        assertThat(leapYear).isEqualTo(true);
    }

    @Test
    void 이번달_첫날_요일_테스트() {
        int day = calendarService.getThisMonthOneDay(2023, 5);
        assertThat(day).isEqualTo(1);

        // 달의 첫날이 일요일일 때 7-> 0으로 변환
        day = calendarService.getThisMonthOneDay(2023, 10);
        assertThat(day).isEqualTo(0);
    }

    @Test
    void 이번달_마지막날_테스트() {
        int day = calendarService.calendarEnd(2023, 5);
        assertThat(day).isEqualTo(31);

        day = calendarService.calendarEnd(2023, 4);
        assertThat(day).isEqualTo(30);

        // 2월은 윤년이면 29일 평년이면 28일
        day = calendarService.calendarEnd(2024, 2);
        assertThat(day).isEqualTo(29);

        day = calendarService.calendarEnd(2023, 2);
        assertThat(day).isEqualTo(28);
    }
    @Test
    void 이번달_마지막_날짜_요일() {
        int day = calendarService.curEndDayOfWeek(2023, 5);
        assertThat(day).isEqualTo(3);

        // 마지막날이 7일때 0으로 변환
        day = calendarService.curEndDayOfWeek(2023, 4);
        assertThat(day).isEqualTo(0);
    }

    @Test
    void 이분탐색_테스트() {
        List<Holiday> list = hs.getHolidayByDate("2023", "5");
        boolean isHoliday = calendarService.binarySearch(list, 5);
        assertThat(isHoliday).isEqualTo(true);

        isHoliday = calendarService.binarySearch(list, 6);
        assertThat(isHoliday).isEqualTo(false);
    }

}