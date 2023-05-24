package tko.refresh.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberJoinDto {
    @NotBlank
    private final String id;
    @NotBlank
    private String password;

    @NotBlank
    @Email
    private final String email;

    @Builder
    public MemberJoinDto(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public void setEncodePwd(String encodePwd) {
        this.password = encodePwd;
    }
}
