package tko.refresh.dto.leaveRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class LeaveRequestDto {
    @NotBlank
    private String memberId;

    @NotBlank
    private double annualCount;

    @NotBlank
    private String annualType;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
