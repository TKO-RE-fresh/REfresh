package tko.refresh.dto.admin;

import lombok.*;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.util.converter.MemberStatusConverter;
import tko.refresh.util.converter.RoleTypeConverter;

import javax.persistence.Convert;
import javax.validation.constraints.*;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberUpdateDto {

    @NotBlank(message = "아이디를 입력하세요.")
    @Size(min = 3, max = 10, message = "아이디를 3자 이상 10자 이하로 입력하세요")
    private String memberId;

    @NotBlank(message = "이름을 입력하세요")
    private String memberName;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호 형식을 확인하세요 : XXX-XXXX-XXXX")
    @NotBlank(message = "전화번호를 입력하세요")
    private String memberCellphone;

    @Email(message = "이메일 형식을 확인하세요.")
    @NotBlank(message = "이메일을 입력하세요")
    private String memberEmail;

    @NotNull(message = "부서를 선택하세요")
    private String departmentName;

    private double annualCount;

    private String createdDate;

    private String modifiedDate;

    private String retireDate;

    @NotNull
    @Convert(converter = RoleTypeConverter.class)
    private RoleType memberAuth;

    @NotNull
    @Convert(converter = MemberStatusConverter.class)
    private MemberStatus memberStatus;

    @Builder
    public MemberUpdateDto(String memberId, String memberName, String memberCellphone, String memberEmail, String departmentName, double annualCount, String createdDate, String modifiedDate, String retireDate, RoleType memberAuth, MemberStatus memberStatus) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberCellphone = memberCellphone;
        this.memberEmail = memberEmail;
        this.departmentName = departmentName;
        this.annualCount = annualCount;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.retireDate = retireDate;
        this.memberAuth = memberAuth;
        this.memberStatus = memberStatus;
    }
}
