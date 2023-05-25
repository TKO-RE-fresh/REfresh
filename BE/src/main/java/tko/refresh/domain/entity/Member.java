package tko.refresh.domain.entity;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tko.refresh.domain.converter.MemberStatusConverter;
import tko.refresh.domain.emb.MemberInfo;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.dto.MemberJoinDto;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "member_uid")
    private UUID id;

    @OneToMany(mappedBy = "member")
    private List<Annual> annuals = new ArrayList<>();

    @Column(name = "member_id", unique = true)
    private String memberId;

    @Column(name = "member_password")
    private String password;

    @Embedded
    private MemberInfo memberInfo;

    @Column(name = "member_annual_count")
    private double annualCount;

    @Column(name = "member_status")
    @Convert(converter = MemberStatusConverter.class)
    private MemberStatus memberStatus;

    @ManyToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "department_uid")
    private Department department;

    @Builder
    public Member(String memberId, String password, MemberInfo memberInfo, double annualCount, MemberStatus memberStatus,
                  Department department, LocalDateTime createdDate, LocalDateTime modifiedDate, String createdBy, String modifiedBy) {
        super(createdBy, modifiedBy, createdDate, modifiedDate);
        this.memberId = memberId;
        this.password = password;
        this.memberInfo = memberInfo;
        this.annualCount = annualCount;
        this.memberStatus = memberStatus;
        this.department = department;
    }

    public void setDepartment(Department d) {
        this.department = d;
    }


    public void addAnnual(Annual annual) {
        annuals.add(annual);
        annual.setMember(this);
    }


}
