package tko.refresh.util.valid;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import tko.refresh.domain.emb.Period;

class ValidPeriodValidatorTest {

    @Test
    void 유효한_날짜_범위() {
        ValidPeriodValidator validPeriodValidator = new ValidPeriodValidator();
        validPeriodValidator.isValid(Period.builder()
                                             .startDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                                             .endDate(LocalDateTime.of(2020, 1, 2, 0, 0))
                                           .build(), null);
    }
    @Test
    void 유효하지_않은_날짜_범위() {
        ValidPeriodValidator validPeriodValidator = new ValidPeriodValidator();
        validPeriodValidator.isValid(Period.builder()
                                             .startDate(LocalDateTime.of(2020, 1, 2, 0, 0))
                                             .endDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                                           .build(), null);
    }
}