package tko.refresh.domain.entity;

import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String refreshToken;

    @Column(unique = true)
    private String accountEmail;

    @Builder
    public RefreshToken(String refreshToken, String accountEmail) {
        this.refreshToken = refreshToken;
        this.accountEmail = accountEmail;
    }


    public RefreshToken updateToken(String refreshToken, String accountEmail) {
        this.refreshToken = refreshToken;
        this.accountEmail = accountEmail;
        return this;
    }
}
