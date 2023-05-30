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
        CalendarReqDto dto = CalendarReqDto.builder().year(1899).month(13)
                                           .deptName("개발팀").build();

        Set<ConstraintViolation<CalendarReqDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());

        dto = CalendarReqDto.builder().year(2023).month(12)
                            .deptName("개발").build();
        violations = validator.validate(dto);
        assertEquals(1, violations.size());


        dto = CalendarReqDto.builder().year(3000).month(13)
                            .deptName("개발").build();
        violations = validator.validate(dto);
        assertEquals(3, violations.size());



        dto = CalendarReqDto.builder().month(12).year(2023)
                            .deptName("개발팀").build();
        violations = validator.validate(dto);

        assertEquals(0, violations.size());
    }

    @Test
    void TodayDto_테스트() {
        GetAnnualByDateAndDeptReqDto dto = GetAnnualByDateAndDeptReqDto.builder().month(0).year(2023).day(1)
                                                                              .deptName("개발팀")
                                                                              .page(1)
                                                                              .size(10)
                                                                              .build();
        Set<ConstraintViolation<GetAnnualByDateAndDeptReqDto>> violations = validator.validate(dto);
        assertEquals(violations.size(), 1);


        dto = GetAnnualByDateAndDeptReqDto.builder()
                .month(1).year(2023).day(1)
                                       .deptName("개발팀")
                                       .page(1)
                                       .size(10)
                                       .build();
        violations = validator.validate(dto);
        assertEquals(violations.size(), 0);
    }
}