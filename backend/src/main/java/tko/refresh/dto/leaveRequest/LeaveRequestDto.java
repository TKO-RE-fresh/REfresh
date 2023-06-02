package tko.refresh.dto.leaveRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class LeaveRequestDto {

    @NotEmpty
    private String memberName;

    private String memberId;

    private double annualCount;

    @NotEmpty
    private String annualType;


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
}
