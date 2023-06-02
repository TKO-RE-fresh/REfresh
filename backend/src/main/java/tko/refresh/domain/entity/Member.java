package tko.refresh.domain.entity;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.dto.admin.MemberUpdateDto;
import tko.refresh.util.converter.MemberStatusConverter;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.util.converter.RoleTypeConverter;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "member_uid")
    private UUID uid;

    @OneToMany(mappedBy = "member")
    private List<Annual> annuals = new ArrayList<>();

    @Column(name = "member_id", unique = true)
    @NotNull
    private String memberId;

    @Column(name = "member_password")
    @NotNull
    private String password;

    @Column(name = "retire_date")
    private LocalDateTime retireDate;

    @Embedded
    @Valid
    private MemberInfo memberInfo;

    @Column(name = "member_annual_count")
    private double annualCount;

    @Column(name = "member_status")
    @Convert(converter = MemberStatusConverter.class)
    private MemberStatus memberStatus;

    @ManyToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "department_uid")
    private Department department;

    @Column(name = "member_auth")
    @NotNull
    @Convert(converter = RoleTypeConverter.class)
    private RoleType memberAuth;

    @Builder
    public Member(String memberId, String password, MemberInfo memberInfo, double annualCount, MemberStatus memberStatus,
                  RoleType memberAuth,
                  Department department, LocalDateTime createdDate, LocalDateTime modifiedDate, LocalDateTime retireDate, String createdBy, String modifiedBy) {
        super(createdBy, modifiedBy, createdDate, modifiedDate);
        this.memberId = memberId;
        this.password = password;
        this.memberInfo = memberInfo;
        this.annualCount = annualCount;
        this.memberAuth = memberAuth;
        this.memberStatus = memberStatus;
        this.department = department;
        this.retireDate = retireDate;
    }

    public void setDepartment(Department d) {
        this.department = d;
    }


    public void addAnnual(Annual annual) {
        annuals.add(annual);
        annual.setMember(this);
    }
    public void updateMember(MemberUpdateDto memberUpdateDto) {
        this.memberInfo=MemberInfo.builder()
                .name(memberUpdateDto.getMemberName())
                .cellphone(memberUpdateDto.getMemberCellphone())
                .email(memberUpdateDto.getMemberEmail())
                .build();
        this.annualCount = memberUpdateDto.getAnnualCount();
        this.modifiedBy = memberUpdateDto.getModifiedBy();
        this.modifiedDate = memberUpdateDto.getModifiedDate();
        this.retireDate = memberUpdateDto.getRetireDate();
        this.memberAuth = memberUpdateDto.getMemberAuth();
        this.memberStatus = memberUpdateDto.getMemberStatus();
    }

<<<<<<< HEAD
=======
    public void updateAnnualCount(double annualCount) {
        this.annualCount = annualCount;
    }



>>>>>>> 33f88d32b6c7da145dc14fb10ba00087b1989ddd
}
