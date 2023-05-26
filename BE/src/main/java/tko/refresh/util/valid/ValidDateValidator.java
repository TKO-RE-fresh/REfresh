package tko.refresh.util.valid;

import java.time.DateTimeException;
import java.time.LocalDate;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import tko.refresh.dto.calendar.TodayDto;

public class ValidDateValidator implements ConstraintValidator<ValidDate, TodayDto> {
    @Override
    public boolean isValid(TodayDto value, ConstraintValidatorContext context) {
        if(value == null) {
            return false;
        }

        int day = value.getDay();
        int month = value.getMonth();
        int year = value.getYear();

        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }


}
