package tko.refresh.dto.calendar.request.holiday;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Daniel Kim
 *
 * Holiday 테이블에 접근하는 Request Dto
 * year : 연도
 * month : 월
 *
 * 2023-04-18
 */
@Getter @ToString @Builder
public class HolidayReqDto {
    @NotBlank
    private final String year;

    @NotBlank
    private final String month;
}
