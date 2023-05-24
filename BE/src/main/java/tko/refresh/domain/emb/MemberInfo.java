package tko.refresh.domain.emb;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberInfo {
    @Column(name = "member_name")
    private String name;

    @Column(name = "member_cellphone", unique = true)
    private String cellphone;

    @Column(name = "member_email", unique = true)
    private String email;

    @Builder
    public MemberInfo(String name, String cellphone, String email) {
        this.name = name;
        this.cellphone = cellphone;
        this.email = email;
    }
}
