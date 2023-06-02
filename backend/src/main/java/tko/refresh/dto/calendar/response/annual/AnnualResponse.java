package tko.refresh.dto.calendar.response.annual;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.util.valid.ValidPeriod;
@Getter
public abstract class AnnualResponse {
    @NotBlank
    private String annualType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    public AnnualResponse(String annualType, LocalDate startDate, LocalDate endDate) {
        this.annualType = annualType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
