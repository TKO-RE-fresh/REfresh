package tko.refresh.dto.member.request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberLoginReqDto {

    @NotBlank
    private  String memberId;
    @NotBlank
    private  String password;

    @Builder

    public MemberLoginReqDto(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }
}
