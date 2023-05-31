package tko.refresh.domain.entity;

import static lombok.AccessLevel.*;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = PROTECTED)
public abstract class BaseEntity {
    @Column(name = "created_nm")
    @NotNull
    protected String createdBy;
    @Column(name = "modified_nm")
    @NotNull
    protected String modifiedBy;

    @NotNull
    protected LocalDateTime createdDate;
    @NotNull
    protected LocalDateTime modifiedDate;

    protected BaseEntity(String createdBy, String modifiedBy, LocalDateTime modifiedDate,
                         LocalDateTime createdDate) {
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
    }

    public BaseEntity(String modifiedBy, LocalDateTime modifiedDate) {
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }
}
