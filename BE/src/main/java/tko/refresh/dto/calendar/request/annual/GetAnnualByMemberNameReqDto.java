package tko.refresh.dto.calendar.request.annual;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tko.refresh.util.page.GlobalPage;

@ToString @Getter
public class GetAnnualByMemberNameReqDto implements GlobalPage {
    @NotBlank
    private final String name;
    @NotNull
    private final Pageable pageable;

    @Builder
    public GetAnnualByMemberNameReqDto(String name, int page, int size) {
        this.name = name;
        this.pageable = setPageable(page, size);
    }


}
