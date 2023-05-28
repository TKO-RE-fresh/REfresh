package tko.refresh.util.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import tko.refresh.domain.emb.Period;

public class ValidPeriodValidator implements ConstraintValidator<ValidPeriod, Period> {
    @Override
    public boolean isValid(Period value, ConstraintValidatorContext context) {
        if(value == null) return false;

        // startdate보다 endDate가 작을때
        if(value.getStartDate().isAfter(value.getEndDate()))
            return false;


        return false;
    }
}
