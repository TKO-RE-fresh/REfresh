package tko.refresh.dto.calendar.request.annual;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tko.refresh.util.page.GlobalPage;
import tko.refresh.util.valid.ValidDeptName;

@ToString @Getter
public class GetAnnualByDeptNameReqDto implements GlobalPage {
    @NotBlank
    @ValidDeptName
    private final String name;

    private final Pageable pageable;
    @Builder
    public GetAnnualByDeptNameReqDto(String name, int page, int size) {
        this.name = name;
        this.pageable = setPageable(page, size);
    }
}
