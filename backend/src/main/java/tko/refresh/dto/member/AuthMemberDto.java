package tko.refresh.dto.member;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.domain.enu.RoleType;
@Builder
@Getter
public class AuthMemberDto {
    // 부서, id, 멤버 이름, 권한, email
    private final String department;
    private final String memberId;

    private final String memberName;

    private final RoleType memberAuth;

    private final String memberEmail;

}
