package tko.refresh.dto.member.request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class MemberLoginReqDto {

    @NotBlank
    private final String memberId;
    @NotBlank
    private final String password;


}
