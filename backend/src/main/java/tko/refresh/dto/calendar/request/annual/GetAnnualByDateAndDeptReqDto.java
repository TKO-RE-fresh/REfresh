package tko.refresh.dto.calendar.request.annual;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tko.refresh.dto.TodayDto;
import tko.refresh.util.page.GlobalPage;
import tko.refresh.util.valid.ValidDate;
import tko.refresh.util.valid.ValidDeptName;

@Getter @ToString
public class GetAnnualByDateAndDeptReqDto implements GlobalPage {
    @ValidDeptName
    private String deptName;

    @ValidDate
    private TodayDto today;


    private Pageable pageable;

    @Builder
    public GetAnnualByDateAndDeptReqDto(int year, int month, int day, int page, int size , String deptName) {
        this.today = TodayDto.builder().year(year).month(month).day(day).build();
        this.deptName = deptName;
        this.pageable = setPageable(page, size);
    }
}
