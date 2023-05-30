package tko.refresh.domain.emb;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberInfo {
    @Column(name = "member_name")
    @NotNull
    private String name;

    @Column(name = "member_cellphone", unique = true)
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Invalid phone number format. Valid format: XXX-XXXX-XXXX")
    @NotNull
    private String cellphone;

    @Column(name = "member_email", unique = true)
    @Email
    @NotNull
    private String email;

    @Builder
    public MemberInfo(String name, String cellphone, String email) {
        this.name = name;
        this.cellphone = cellphone;
        this.email = email;
    }
}
