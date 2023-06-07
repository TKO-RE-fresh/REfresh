package tko.refresh.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class SearchFormDto {

    private List<String> departmentNameList;

    private List<MemberStatus> memberStatusList;

    private List<RoleType> memberAuthList;

    @Builder
    public SearchFormDto(List<String> departmentNameList, List<MemberStatus> memberStatusList, List<RoleType> memberAuthList) {
        this.departmentNameList = departmentNameList;
        this.memberStatusList = memberStatusList;
        this.memberAuthList = memberAuthList;
    }
}
