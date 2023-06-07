package tko.refresh.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import org.springframework.lang.Nullable;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.util.converter.AnnualTypeConverter;

import javax.persistence.Convert;

@Getter
@ToString
public class AnnualSearchDto {

    @Nullable
    private String memberName;

    @Nullable
    private String departmentName;

    @Nullable
    @Convert(converter = AnnualTypeConverter.class)
    private AnnualStatus status;

    @Builder
    public AnnualSearchDto(String memberName, String departmentName, AnnualStatus status) {
        this.memberName = memberName;
        this.departmentName = departmentName;
        this.status = status;
    }
}
