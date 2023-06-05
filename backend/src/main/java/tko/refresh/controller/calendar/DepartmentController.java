package tko.refresh.controller.calendar;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tko.refresh.dto.calendar.request.annualcount.AnnualCountReqDto;
import tko.refresh.repository.calendar.DepartmentEmRepository;
import tko.refresh.repository.calendar.DepartmentRepository;

@RestController
@RequestMapping("/calendar/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentEmRepository departmentEmRepository;

    @GetMapping
    public ResponseEntity getAllDepartment() {
        // 모든 부서명 조회
        return ResponseEntity.ok().body(departmentEmRepository.findAllDeptName());
    }

}
