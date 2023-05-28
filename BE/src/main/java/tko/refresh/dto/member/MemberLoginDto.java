package tko.refresh.dto.member;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MemberLoginDto {

    @NotBlank
    private final String memberId;
    @NotBlank
    private final String password;


}
