package tko.refresh.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tko.refresh.dto.calendar.request.annual.GetAnnualByDateAndDeptReqDto;
import tko.refresh.dto.calendar.request.calendar.CalendarReqDto;

class DtoTest {

    private Validator validator;
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    void YearMonth_DeptName_dto_테스트() {
        CalendarReqDto dto = CalendarReqDto.builder().yearMonth(YearMonthDto.builder()
                                                                            .month(2023).year(0).build())
                                           .deptName("개발팀").build();

        Set<ConstraintViolation<CalendarReqDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());

        dto = CalendarReqDto.builder().yearMonth(YearMonthDto.builder()
                                                             .month(2).year(1950).build())
                            .deptName("개발").build();
        violations = validator.validate(dto);
        assertEquals(1, violations.size());


        dto = CalendarReqDto.builder().yearMonth(YearMonthDto.builder()
                                                             .month(2023).year(1).build())
                            .deptName("개발").build();
        violations = validator.validate(dto);
        assertEquals(3, violations.size());



        dto = CalendarReqDto.builder().yearMonth(YearMonthDto.builder()
                                                             .month(12).year(2023).build())
                            .deptName("개발팀").build();
        violations = validator.validate(dto);

        assertEquals(0, violations.size());
    }

    @Test
    void TodayDto_테스트() {
        GetAnnualByDateAndDeptReqDto dto = GetAnnualByDateAndDeptReqDto.builder()
                                                                        .today(TodayDto.builder()
                                                                                        .day(0)
                                                                                        .month(0)
                                                                                        .year(0)
                                                                                        .build())
                                                                        .deptName("개발팀")
                                                                        .page(1)
                                                                        .size(10)
                                                                        .build();
        Set<ConstraintViolation<GetAnnualByDateAndDeptReqDto>> violations = validator.validate(dto);
        assertEquals(violations.size(), 1);


        dto = GetAnnualByDateAndDeptReqDto.builder()
                                          .today(TodayDto.builder()
                                                         .day(1)
                                                         .month(1)
                                                         .year(2023)
                                                         .build())
                                          .deptName("개발팀")
                                          .page(1)
                                          .size(10)
                                          .build();
        violations = validator.validate(dto);
        assertEquals(violations.size(), 0);
    }
}