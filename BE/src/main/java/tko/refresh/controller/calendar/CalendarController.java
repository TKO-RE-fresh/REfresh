package tko.refresh.controller.calendar;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import tko.refresh.service.calendar.CalendarService;
import tko.refresh.service.calendar.DepartmentService;

@RestController
@RequestMapping("/calendar")
@CrossOrigin("*")
//@RequiredArgsConstructor
public class CalendarController {

//    private CalendarService calendarService;
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity getCalendar(@RequestParam String year, @RequestParam String month) {

        return ResponseEntity.ok().build();
    }
}
