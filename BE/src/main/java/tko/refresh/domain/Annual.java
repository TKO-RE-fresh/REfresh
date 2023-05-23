package tko.refresh.domain;

import static lombok.AccessLevel.*;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tko.refresh.domain.converter.AnnualStatusConverter;
import tko.refresh.domain.converter.AnnualTypeConverter;
import tko.refresh.domain.value.enu.AnnualStatus;
import tko.refresh.domain.value.enu.AnnualType;
import tko.refresh.domain.value.emb.Period;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Annual extends BaseEntity {
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "annual_uid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uid")
    @NotNull
    private Member member;
    @Column(name = "annual_type")
    @Convert(converter = AnnualTypeConverter.class)
    @NotNull
    private AnnualType annualType;

    @Column(name = "annual_status")
    @Convert(converter = AnnualStatusConverter.class)
    @NotNull
    private AnnualStatus annualStatus;

    @NotNull
    private String acceptor;

    @Embedded
    private Period period;

    @Builder
    public Annual(Member member, AnnualType annualType, AnnualStatus annualStatus, String acceptor, Period period, String createdBy, String modifiedBy, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdBy, modifiedBy, createdDate, modifiedDate);
        this.annualStatus = annualStatus;
        this.acceptor = acceptor;
        this.member = member;
        this.period = period;
        this.annualType = annualType;
    }
}
