package tko.refresh.dto.member;

import static lombok.AccessLevel.PROTECTED;

import javax.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class TokenDto {
    @NotBlank
    private final String accessToken;

    @NotBlank
    private final String refreshToken;


}
