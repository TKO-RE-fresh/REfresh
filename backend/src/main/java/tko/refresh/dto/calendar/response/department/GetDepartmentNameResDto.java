package tko.refresh.dto.calendar.response.department;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class GetDepartmentNameResDto {
    private final String name;

    @Builder
    public GetDepartmentNameResDto(String name) {
        this.name = name;
    }

}
