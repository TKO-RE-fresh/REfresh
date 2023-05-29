package tko.refresh.dto.calendar.response.annual;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.util.valid.ValidPeriod;
@Getter
public abstract class AnnualResponse {
    @NotNull
    private AnnualType annualType;

    @NotNull
    @ValidPeriod
    private Period period;

    public AnnualResponse(AnnualType annualType, Period period) {
        this.annualType = annualType;
        this.period = period;
    }
}
