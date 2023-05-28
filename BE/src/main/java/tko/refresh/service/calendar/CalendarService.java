package tko.refresh.service.calendar;

import static tko.refresh.dto.calendar.response.calendar.CalendarResDto.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tko.refresh.domain.entity.Holiday;
import tko.refresh.dto.calendar.request.annualcount.AnnualCountReqDto;
import tko.refresh.dto.calendar.request.calendar.CalendarReqDto;
import tko.refresh.dto.calendar.response.annualcount.AnnualCountResDto;
import tko.refresh.dto.calendar.response.calendar.CalendarResDto;
import tko.refresh.repository.calendar.AnnualCountEmRepository;

/**
 * Daniel Kim
 *
 * 달력 데이터를 만드는 로직 모음 클래스
 *
 * 2023-04-19
 */
@Service
@RequiredArgsConstructor
public class CalendarService {
    /**
     * Daniel Kim
     *
     * @param dto
     * @return List<Integer>
     * 연도와 월을 파라미터로 받아 현재 달 정보와 전 달의 정보를 이용해
     * 이번 달의 정보를 생성하는 메소드
     * 연차 집계 데이터 리스트를 가져와서 연차 집계 데이터를 이번 달 달력에 표시한다.
     *
     * 2023-04-19
     */
    private static String holidayName;
    private final AnnualCountEmRepository annualCountRepository;
    private final HolidayService holidayService;



    public List<CalendarResDto> updateCalendar(CalendarReqDto calendarReqDto) {
        List<CalendarResDto> result = new ArrayList<>();
        int year = calendarReqDto.getYearMonth().getYear();
        int month = calendarReqDto.getYearMonth().getMonth();


        // 현재 달 정보
        int dayIdx = 1;
        int curDay = getThisMonthOneDay(year, month);
        int curEnd = calendarEnd(year, month);

        // 전 달의 정보
        int preEnd = calendarEnd(year, month - 1);
        int preStart = preEnd - curDay + 1;


        // 다음 달 표시 정보 (0 일때는 6 1일때는 5개 2일때는 4개 ... 5일때는 1 6일때는 0)
        // 1일부터 시작
        int nextDay = 1;
        int nextEnd = 6 - curEndDayOfWeek(year, month);

        // 휴일 정보
        List<Holiday> holidayDataList = holidayService.getHolidayByDate(Integer.toString(year), Integer.toString(month));

        // 전달 정보 생성
        while(preStart <= preEnd) {
            result.add(builder().day(preStart++)
                                .hoName("")
                                .build());
        }

        List<AnnualCountResDto> annualCountByDept = annualCountRepository.getAnnualCountByDept(
                AnnualCountReqDto.builder()
                                 .yearMonth(calendarReqDto.getYearMonth())
                                 .deptName(calendarReqDto.getDeptName())
                                 .build());

        int minimumDay = annualCountByDept.size() == 0 ? 0 :
                         annualCountByDept.get(0).getAnnualDate().getDayOfMonth();

        int dayCount = 0;

        // 이번달 정보 생성 (요일 정보, 휴일 정보, 이번 달 표시 정보)
        while(dayIdx <= curEnd) {
            curDay = curDay == 7 ? 0 : curDay;
            CalendarResDtoBuilder builder = builder();

            if(dayIdx >= minimumDay && minimumDay > 0) {
                builder.sumCount(annualCountByDept.get(dayCount++).getSumCount());
            }

            if(binarySearch(holidayDataList, dayIdx)) {
                builder.day(dayIdx++)
                       .hoName(holidayName);
            } else {
                if(curDay == 0) {
                    builder.day(dayIdx++).hoName("일요일");
                } else if (curDay == 6) {
                    builder.day(dayIdx++).hoName("토요일");
                } else {
                    builder.day(dayIdx++).hoName("평일");
                }
            }

            result.add(builder.build());
            curDay++;
        }
        // 다음달 정보 생성
        while(nextDay <= nextEnd) {
            result.add(builder()
                                         .day(nextDay++)
                                         .hoName("")
                                         .build());
        }
        return result;
    }

    /**
     * Daniel Kim
     *
     * @param holidayDataList
     * @param dayIdx
     * @return boolean
     * 현재 HolidayServiceDTO 에 해당 휴일이 있는지 찾는 이분 탐색 메소드
     *
     * 2023-04-22
     */
    public static boolean binarySearch(List<Holiday> holidayDataList, int dayIdx) {
        int left = 0;
        int right = holidayDataList.size() - 1;
        int mid;
        int midIdx;
        while(left <= right) {
            mid = (left + right) / 2;
            midIdx = holidayDataList.get(mid).getDay();
            if(midIdx == dayIdx) {
                holidayName = holidayDataList.get(mid).getName();
                return true;
            }
            if(midIdx < dayIdx) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
    /**
     * Daniel Kim
     *
     * @param year
     * @param month
     * @return int
     * 연과 월을 파라미터로 받아 그 달의 마지막 날짜의 요일 Index 를 반환하여 마지막 달 표시 정보를 구성
     *
     * 2023-04-19
     */
    public int curEndDayOfWeek(int year, int month) {
        int idx = LocalDate.of(year, month, calendarEnd(year, month)).getDayOfWeek().getValue();
        return idx == 7 ? 0 : idx;
    }
    /**
     * Daniel Kim
     *
     * @param year
     * @param month
     * @return int
     * 그 달의 마지막 일자를 구하는 메소드
     *
     * 2023-04-19
     */
    public int calendarEnd(int year, int month) {
        // 윤년이면
        if(getLeapYear(year) && month == 2) return 29;

        if(month == 2) return 28;

        if(month < 8 && month % 2 == 1) return 31;

        if(month >= 8 && month % 2 == 0) return 31;

        return 30;
    }
    /**
     * Daniel Kim
     *
     * @param year
     * @param month
     * @return int
     * 이번 달 1일의 요일 index 를 구하는 메소드
     *
     * 2023-04-19
     */
    public int getThisMonthOneDay(int year, int month) {
        int dayIdx = LocalDate.of(year, month, 1).getDayOfWeek().getValue();

        return dayIdx == 7 ? 0 : dayIdx;
    }

    /**
     * Daniel Kim
     *
     * @param year
     * @return boolean
     * 윤년인지 아닌지 구하는 메소드
     *
     * 2023-04-17
     */
    public boolean getLeapYear(int year) {
        if(year % 4 == 0) {
            if(year % 100 == 0) {
                if(year % 400 == 0) return true;

                return false;
            }
            return true;
        }
        return false;
    }
}