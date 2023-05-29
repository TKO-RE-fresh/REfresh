package tko.refresh.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberJoinDto {
    private final String memberId;
    private String password;

    private final String email;

    private final String deptName;

    public void setEncodePwd(String encode) {
        this.password = encode;
    }
}
