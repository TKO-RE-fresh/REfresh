package tko.refresh.domain;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "annual_count")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class AnnualCount {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "sum_count")
    private int sumCount;

    @Column(name = "annual_date")
    @NotNull
    private LocalDateTime annualDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_uid")
    @NotNull
    private Department department;

    @Builder
    public AnnualCount(Department department, int sumCount, LocalDateTime annualDate) {
        this.sumCount = sumCount;
        this.annualDate = annualDate;
        this.department = department;
    }

}
