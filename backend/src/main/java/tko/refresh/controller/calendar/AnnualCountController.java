package tko.refresh.controller.calendar;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.calendar.request.annualcount.AnnualCountReqDto;
import tko.refresh.dto.calendar.response.annualcount.AnnualCountResDto;
import tko.refresh.repository.calendar.AnnualCountEmRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar/annualCount")
public class AnnualCountController {

    private final AnnualCountEmRepository annualCountEmRepository;

    @GetMapping
    public ResponseEntity getCalendarByDepartmentName(@ModelAttribute @Valid AnnualCountReqDto dto) {
        List<AnnualCountResDto> collect = annualCountEmRepository.getAnnualCountByDept(dto);
        return ResponseEntity.ok().body(collect);
    }
}
