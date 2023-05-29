package tko.refresh.dto.calendar.response.department;

import lombok.Builder;

public class GetDepartmentNameResDto {
    private final String name;

    @Builder
    public GetDepartmentNameResDto(String name) {
        this.name = name;
    }

}
