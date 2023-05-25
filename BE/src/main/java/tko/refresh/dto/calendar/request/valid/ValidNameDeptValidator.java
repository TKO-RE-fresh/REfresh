package tko.refresh.dto.calendar.request.valid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidNameDeptValidator implements ConstraintValidator<ValidDeptName, String> {

    private final static Set<String> validNames = new HashSet<>();

    static {
        validNames.add("개발팀");
        validNames.add("기획팀");
        validNames.add("영업팀");
        validNames.add("디자인팀");
        validNames.add("인사팀");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 유효한 이름 목록
        List<String> validDeptNames = Arrays.asList("개발팀", "기획팀", "영업팀", "디자인팀", "인사팀");

        if(value != null && !value.isEmpty() && validDeptNames.contains(value))
            return true;

        return false;
    }
}
