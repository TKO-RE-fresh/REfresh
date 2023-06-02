package tko.refresh.dto.calendar.response.annual;
import java.util.List;

import javax.validation.Valid;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AnnualResponseDto<T extends AnnualResponse> {

    @Valid
    private final List<T> content;

    private final long totalPage;

    private final int totalElements;
    private final int curPage;


}
