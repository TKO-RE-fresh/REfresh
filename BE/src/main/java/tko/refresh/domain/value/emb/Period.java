package tko.refresh.domain.value.emb;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Embeddable
@Getter
public class Period {
    @Column(name = "start_date")
    @NotNull
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @NotNull
    private LocalDateTime endDate;

}
