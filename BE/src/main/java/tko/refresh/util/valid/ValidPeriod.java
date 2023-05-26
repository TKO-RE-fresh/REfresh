package tko.refresh.util.valid;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Constraint(validatedBy = ValidPeriodValidator.class)
@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeriod {
    String message() default "Invalid Period";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
