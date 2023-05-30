package tko.refresh.dto.admin;

import lombok.Getter;
import org.springframework.lang.Nullable;
import tko.refresh.domain.enu.AnnualStatus;

import java.util.UUID;

@Getter
public class AnnualStatusDto {

   private String uid;

   @Nullable
   private String rejectReason;
}
