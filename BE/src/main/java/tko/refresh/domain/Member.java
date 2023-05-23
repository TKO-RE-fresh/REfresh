package tko.refresh.domain;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tko.refresh.domain.converter.MemberStatusConverter;
import tko.refresh.domain.value.enu.MemberStatus;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)

public class Member extends BaseEntity {
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "member_uid")
    private UUID uid;

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Annual> annual = new ArrayList<>();

    @Column(name = "member_id")
    @NotNull
    private String id;

    @Column(name = "member_password")
    @NotNull
    private String password;

    @Column(name = "member_name")
    @NotNull
    private String name;

    @Column(name = "member_cellphone")
    @NotNull
    private String cellphone;

    @Column(name = "member_email")
    @NotNull
    private String email;

    @Column(name = "annual_count")
    @NotNull
    private double annualCount;

    @Column(name = "member_status")
    @Convert(converter = MemberStatusConverter.class)
    @NotNull
    private MemberStatus memberStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_uid")
    private Department department;

    @Builder
    public Member(String id, String password, String name, String cellphone, String email, double annualCount, MemberStatus memberStatus,
                  Department department, LocalDateTime createdDate, LocalDateTime modifiedDate, String createdBy, String modifiedBy) {
        super(createdBy, modifiedBy, createdDate, modifiedDate);
        this.id = id;
        this.password = password;
        this.cellphone = cellphone;
        this.email = email;
        this.annualCount = annualCount;
        this.memberStatus = memberStatus;
        this.name = name;
        this.department = department;
    }

}
