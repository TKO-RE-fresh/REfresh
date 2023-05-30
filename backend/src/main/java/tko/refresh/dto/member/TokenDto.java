package tko.refresh.dto.member;

import static lombok.AccessLevel.PROTECTED;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class TokenDto {
    @NotBlank
    private final String accessToken;

    @NotBlank
    private final String refreshToken;


}
