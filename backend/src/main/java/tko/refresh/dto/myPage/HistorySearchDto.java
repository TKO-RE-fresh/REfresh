package tko.refresh.dto.myPage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.util.converter.AnnualStatusConverter;
import tko.refresh.util.converter.AnnualTypeConverter;

import javax.persistence.Convert;

@Getter
@ToString
public class HistorySearchDto {

    @Nullable
    @Convert(converter = AnnualStatusConverter.class)
    private AnnualStatus status;

    @Nullable
    @Convert(converter = AnnualTypeConverter.class)
    private AnnualType type;

    @Nullable
    private int year;

    @Builder
    public HistorySearchDto(AnnualType type, AnnualStatus status, int year){
        this.status = status;
        this.type = type;
        this.year = year;
    }
}
