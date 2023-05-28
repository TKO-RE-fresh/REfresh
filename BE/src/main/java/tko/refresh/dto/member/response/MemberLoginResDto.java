package tko.refresh.dto.member.response;

import lombok.Builder;
import lombok.Getter;
import tko.refresh.util.valid.ValidDeptName;

@Builder
@Getter
public class MemberLoginResDto {
    private final String auth;

    private final String memberId;

    @ValidDeptName
    private final String deptName;

    private final String memberName;


    private final String message;

    private int statusCode;
}
