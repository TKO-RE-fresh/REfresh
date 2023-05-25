package tko.refresh.controller.calendar;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.calendar.request.GetAnnualByDateAndDeptReqDto;
import tko.refresh.dto.calendar.request.GetAnnualByNameReqDto;
import tko.refresh.dto.calendar.TodayDto;
import tko.refresh.dto.calendar.response.GetAnnualByDateAndDeptResDto;
import tko.refresh.dto.calendar.response.GetAnnualByNameResDto;
import tko.refresh.repository.calendar.AnnualEmRepository;

@RestController
@RequestMapping("/calendar/annual")
@RequiredArgsConstructor
public class AnnualController {

    private final AnnualEmRepository annualRepository;

    /**
     * Daniel Kim
     *
     * @param year
     * @param month
     * @param day
     * @param name
     * @param page
     * @param size
     * @return
     *
     * 연, 월, 일, 부서 이름과 페이지 범위를 통해 연차 데이터를 가져옴
     *
     * 2023-05-25
     */

    @GetMapping
    public ResponseEntity getAnnualByDateAndDept(@RequestParam int year, @RequestParam int month, @RequestParam int day, @RequestParam String name,
                                                 @RequestParam int page, @RequestParam int size) {
     List<GetAnnualByDateAndDeptResDto> content = annualRepository.findAnnualByDateAndDept(
             GetAnnualByDateAndDeptReqDto.builder()
                                         .today(TodayDto.builder().day(day).month(month).year(year).build())
                                         .deptName(name)
                                         .pageable(Pageable.ofSize(size).withPage(page - 1)).build());


        return ResponseEntity.ok().body(content);
    }
    /**
     * Daniel Kim
     *
     * @param name
     * @param page
     * @param size
     * @return
     * 사원 이름과 페이지 범위를 통해 연차 데이터를 가져옴
     *
     * 2023-05-24
     */
    @GetMapping("/member")
    public ResponseEntity getAnnualByMember(@RequestParam String name,
                                            @RequestParam int page,
                                            @RequestParam int size) {
        List<GetAnnualByNameResDto> content = annualRepository.findAnnualByMember(GetAnnualByNameReqDto
                                                                                     .builder()
                                                                                     .name(name)
                                                                                     .pageable(Pageable.ofSize(size).withPage(page - 1))
                                                                                          .build()).getContent();
        return ResponseEntity.ok().body(content);
    }

    /**
     * Daniel Kim
     *
     * @param name
     * @param page
     * @param size
     * @return
     * 부서 이름과 페이지 범위를 통해 연차 데이터를 가져옴
     *
     * 2023-05-24
     */

    @GetMapping("/department")
    public ResponseEntity getAnnualByDept(@RequestParam String name,
                                                    @RequestParam int page,
                                                    @RequestParam int size) {
        List<GetAnnualByNameResDto> content = annualRepository.findAnnualByDept(GetAnnualByNameReqDto
                                                                                     .builder()
                                                                                     .name(name)
                                                                                     .pageable(Pageable.ofSize(size).withPage(page - 1))
                                                                                     .build())
                                                                                     .getContent();
        return ResponseEntity.ok().body(content);
    }

}
