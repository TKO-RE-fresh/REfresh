package tko.refresh.controller.calendar;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calendar/department")
public class DepartmentController {

    @GetMapping
    public ResponseEntity getAllDepartment() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity getCalendarByDepartmentName(@RequestParam String year, @RequestParam String month) {
        return ResponseEntity.ok().build();
    }
}
