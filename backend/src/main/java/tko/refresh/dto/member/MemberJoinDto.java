package tko.refresh.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.util.converter.MemberStatusConverter;
import tko.refresh.util.converter.RoleTypeConverter;

import javax.persistence.Convert;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@ToString
public class MemberJoinDto {

    @NotNull
    private final String memberId;

    @NotNull
    private String password;

    @Convert(converter = RoleTypeConverter.class)
    private RoleType memberAuth;

    @NotNull
    private String memberName;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Invalid phone number format. Valid format: XXX-XXXX-XXXX")
    @NotNull
    private String memberCellphone;

    @Email
    @NotNull
    private String memberEmail;

    @NotNull
    @Convert(converter = MemberStatusConverter.class)
    private MemberStatus memberStatus;

    private double annualCount;

    private String departmentName;

    private String createdDate;

    private String modifiedBy;

    private String createdBy;

    public void setEncodePwd(String encode) {
        this.password = encode;
    }
}
