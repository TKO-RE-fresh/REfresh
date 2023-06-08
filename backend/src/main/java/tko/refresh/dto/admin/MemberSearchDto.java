package tko.refresh.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.util.converter.MemberStatusConverter;

import javax.persistence.Convert;

@Getter
@Setter
public class MemberSearchDto {

    @Nullable
    private String memberName;

    @Nullable
    private String departmentName;

    @Nullable
    @Convert(converter = MemberStatusConverter.class)
    private MemberStatus status;

    @Builder
    public MemberSearchDto(@Nullable String memberName, @Nullable String departmentName, @Nullable MemberStatus status) {
        this.memberName = memberName;
        this.departmentName = departmentName;
        this.status = status;
    }
}

