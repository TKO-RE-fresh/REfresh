package tko.refresh.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.util.converter.MemberStatusConverter;
import tko.refresh.util.converter.RoleTypeConverter;

import javax.persistence.Convert;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberDetailDto {

    private String memberId;

    private String memberName;

    private String departmentName;

    private String memberCellphone;

    private String memberEmail;

    private Double annualCount;

    protected String createdBy;

    protected String modifiedBy;

    private LocalDateTime createdDate;

    protected LocalDateTime modifiedDate;

    private LocalDateTime retireDate;

    @Convert(converter = MemberStatusConverter.class)
    private MemberStatus memberStatus;

    @Convert(converter = RoleTypeConverter.class)
    private RoleType memberAuth;

    private List<String> departmentNameList;

    private List<MemberStatus> memberStatusList;

    private List<RoleType> roleTypeList;

    @Builder
    public MemberDetailDto(String memberId, String memberName, String departmentName, String memberCellphone, String memberEmail, double annualCount, String createdBy, String modifiedBy, LocalDateTime createdDate, LocalDateTime modifiedDate, LocalDateTime retireDate, MemberStatus memberStatus, RoleType memberAuth, List<String> departmentNameList, List<MemberStatus> memberStatusList, List<RoleType> roleTypeList) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.departmentName = departmentName;
        this.memberCellphone = memberCellphone;
        this.memberEmail = memberEmail;
        this.annualCount = annualCount;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.retireDate = retireDate;
        this.memberStatus = memberStatus;
        this.memberAuth = memberAuth;
        this.departmentNameList = departmentNameList;
        this.memberStatusList = memberStatusList;
        this.roleTypeList = roleTypeList;
    }
}
