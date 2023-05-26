package tko.refresh.util.valid;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tko.refresh.dto.calendar.TodayDto;

class ValidDateValidatorTest {

    @Test
    void 유효하지않은_날짜() {
        ValidDateValidator validDateValidator = new ValidDateValidator();
        TodayDto dto1 = TodayDto.builder()
                .day(32)
                .month(12)
                .year(2020)
                .build();
        assertFalse(validDateValidator.isValid(dto1, null));
        TodayDto dto2 = TodayDto.builder()
                               .day(25)
                               .month(13)
                               .year(2020)
                               .build();
        assertFalse(validDateValidator.isValid(dto1, null));

    }
    @Test
    void 유효한_날짜() {
        ValidDateValidator validDateValidator = new ValidDateValidator();
        TodayDto dto = TodayDto.builder()
                .day(25)
                .month(12)
                .year(2020)
                .build();
        assertTrue(validDateValidator.isValid(dto, null));
    }
}