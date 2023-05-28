package tko.refresh.dto.calendar.response.global;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalResponseDto {

    @NotBlank
    private String msg;
    @NotBlank
    private int statusCode;

    @Builder
    public GlobalResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

}