package tko.refresh.dto.admin;

import lombok.*;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.util.converter.MemberStatusConverter;
import tko.refresh.util.converter.RoleTypeConverter;

import javax.persistence.Convert;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberUpdateDto {

    @NotNull
    private String memberId;

    @NotNull
    private String memberName;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Invalid phone number format. Valid format: XXX-XXXX-XXXX")
    @NotNull
    private String memberCellphone;

    @Email
    @NotNull
    private String memberEmail;

    private String departmentName;

    private double annualCount;

    private  String modifiedBy;

    private LocalDateTime modifiedDate;

    private LocalDateTime retireDate;

    @NotNull
    @Convert(converter = RoleTypeConverter.class)
    private RoleType memberAuth;

    @NotNull
    @Convert(converter = MemberStatusConverter.class)
    private MemberStatus memberStatus;

    @Builder
    public MemberUpdateDto(String memberId, String memberName, String memberCellphone, String memberEmail, String departmentName, double annualCount, String modifiedBy, LocalDateTime modifiedDate, LocalDateTime retireDate, RoleType memberAuth, MemberStatus memberStatus) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberCellphone = memberCellphone;
        this.memberEmail = memberEmail;
        this.departmentName = departmentName;
        this.annualCount = annualCount;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.retireDate = retireDate;
        this.memberAuth = memberAuth;
        this.memberStatus = memberStatus;
    }
}
