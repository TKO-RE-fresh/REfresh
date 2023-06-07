package tko.refresh.dto.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.util.converter.DateConverter;
import tko.refresh.util.converter.MemberStatusConverter;

import javax.persistence.Convert;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class MemberDto {

    @NotEmpty
    private String memberId;
    @NotEmpty
    private String memberName;
    @NotEmpty
    private String departmentName;
    @NotEmpty
    private String memberCellphone;
    @NotEmpty
    private String memberEmail;
    @NotEmpty
    private LocalDateTime createdDate;
    private LocalDateTime retireDate;

    @Convert(converter = MemberStatusConverter.class)
    private MemberStatus memberStatus;

    @Builder
    @QueryProjection
    public MemberDto(String memberId, String memberName, String departmentName, String memberCellphone, String memberEmail, LocalDateTime createdDate, LocalDateTime retireDate, MemberStatus memberStatus) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.departmentName = departmentName;
        this.memberCellphone = memberCellphone;
        this.memberEmail = memberEmail;
        this.createdDate = createdDate;
        this.retireDate = retireDate;
        this.memberStatus = memberStatus;
    }
}
