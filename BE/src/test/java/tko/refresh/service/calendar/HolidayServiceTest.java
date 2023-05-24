package tko.refresh.service.calendar;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tko.refresh.domain.entity.Holiday;

/**
 * Daniel Kim
 * HolidayServiceTest : Open API 데이터 fetching, DB 연동에 대한 테스트 클래스
 * 2023-04-19
 */
@SpringBootTest
class HolidayServiceTest {
    @Autowired
    private HolidayService holidayService;


//    @Test
//    void 휴일_정보_삽입() {
//       getOpenApiHolidayData();
//    }

    /**
     * Daniel Kim
     *
     * 휴일 정보를 DB 에서 가져오는 Test
     *
     * 2023-04-19
     */
    @Test
    void 휴일_정보_DB_에서_가져오기() {
        // 0개의 휴일 테스트
        String year1 = "1900";
        String month1 = "11";
        List<Holiday> holidayList1 = holidayService.getHolidayByDate(year1, month1);
        assertThat(holidayList1.size()).isEqualTo(0);

        // 휴일이 있을때 테스트
        String year = "2023";
        String month = "5";
        List<Holiday> holidayList2 = holidayService.getHolidayByDate(year, month);
        assertThat(holidayList2.size()).isGreaterThan(0);

    }
}