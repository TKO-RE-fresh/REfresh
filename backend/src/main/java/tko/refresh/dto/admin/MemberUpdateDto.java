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
    private final String memberName;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Invalid phone number format. Valid format: XXX-XXXX-XXXX")
    @NotNull
    private final String memberCellphone;

    @Email
    @NotNull
    private final String memberEmail;

    private final double annualCount;

    private final String modifiedBy;

    private final LocalDateTime modifiedDate;

    private final LocalDateTime retireDate;

    @NotNull
    @Convert(converter = RoleTypeConverter.class)
    private final RoleType memberAuth;

    @Convert(converter = MemberStatusConverter.class)
    private MemberStatus memberStatus;

    @Builder
    public MemberUpdateDto(String memberName, String memberCellphone, String memberEmail, double annualCount, String modifiedBy, LocalDateTime modifiedDate, LocalDateTime retireDate, RoleType memberAuth, MemberStatus memberStatus) {
        this.memberName = memberName;
        this.memberCellphone = memberCellphone;
        this.memberEmail = memberEmail;
        this.annualCount = annualCount;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.retireDate = retireDate;
        this.memberAuth = memberAuth;
        this.memberStatus = memberStatus;
    }
}
