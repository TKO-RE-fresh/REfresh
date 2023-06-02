package tko.refresh.dto.admin;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import tko.refresh.domain.enu.AnnualStatus;

import java.util.UUID;

@Getter
@ToString
public class AnnualStatusDto {

   private UUID uid;

   private String status;

   @Nullable
   private String rejectReason;
}
