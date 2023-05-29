package tko.refresh.controller.calendar;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.calendar.request.annual.GetAnnualByDateAndDeptReqDto;
import tko.refresh.dto.calendar.request.annual.GetAnnualByDeptNameReqDto;
import tko.refresh.dto.calendar.request.annual.GetAnnualByMemberNameReqDto;
import tko.refresh.dto.calendar.response.annual.AnnualResponse;
import tko.refresh.dto.calendar.response.annual.AnnualResponseDto;
import tko.refresh.repository.calendar.AnnualEmRepository;

@RestController
@RequestMapping("/calendar/annual")
@RequiredArgsConstructor
public class AnnualController {

    private final AnnualEmRepository annualRepository;

    /**
     * Daniel Kim
     *
     * @param dto
     * @return
     *
     * 연, 월, 일, 부서 이름과 페이지 범위를 통해 연차 데이터를 가져옴
     *
     * 2023-05-25
     */

    @GetMapping
    public ResponseEntity getAnnualByDateAndDept(@ModelAttribute GetAnnualByDateAndDeptReqDto dto) {
        return ResponseEntity.ok().body(annualRepository.findAnnualByDateAndDept(dto));
    }
    /**
     * Daniel Kim
     *
     * @param dto
     * @return
     * 사원 이름과 페이지 범위를 통해 연차 데이터를 가져옴
     *
     * 2023-05-24
     */
    @GetMapping("/member")
    public ResponseEntity getAnnualByMember(@ModelAttribute @Valid GetAnnualByMemberNameReqDto dto){
        AnnualResponseDto<AnnualResponse> content = annualRepository.findAnnualByMember(dto);
        return ResponseEntity.ok().body(content);
    }

    /**
     * Daniel Kim
     *
     * @param dto
     * @return
     * 부서 이름과 페이지 범위를 통해 연차 데이터를 가져옴
     *
     * 2023-05-24
     */

    @GetMapping("/department")
    public ResponseEntity getAnnualByDept(@ModelAttribute @Valid GetAnnualByDeptNameReqDto dto) {
        AnnualResponseDto<AnnualResponse> content = annualRepository.findAnnualByDept(dto);
        return ResponseEntity.ok().body(content);
    }

}
