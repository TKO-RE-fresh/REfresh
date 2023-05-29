package tko.refresh.util.valid;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ValidNameDeptValidatorTest {

    @Test
    void 유효하지_않은_부서이름() {
        ValidNameDeptValidator validNameDeptValidator = new ValidNameDeptValidator();
        assertTrue(validNameDeptValidator.isValid("개발팀", null));
    }

    @Test
    void 유효한_부서이름() {
        ValidNameDeptValidator validNameDeptValidator = new ValidNameDeptValidator();
        assertFalse(validNameDeptValidator.isValid("부서이름", null));
    }

}