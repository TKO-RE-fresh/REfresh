package tko.refresh.dto.calendar.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder @ToString @Getter
public class GetAnnualByNameReqDto {


    @NotBlank
    private final String name;
    @NotNull
    private final Pageable pageable;



}
