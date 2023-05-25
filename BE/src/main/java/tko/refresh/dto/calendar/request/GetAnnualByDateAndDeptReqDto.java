package tko.refresh.dto.calendar.request;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.dto.calendar.TodayDto;
import tko.refresh.dto.calendar.request.valid.ValidDate;
import tko.refresh.dto.calendar.request.valid.ValidDeptName;

@Builder
@Getter
public class GetAnnualByDateAndDeptReqDto {
    @Valid
    @ValidDate
    private TodayDto today;
    @NotBlank
    @ValidDeptName
    private final String deptName;
    @Valid
    private final Pageable pageable;
}
