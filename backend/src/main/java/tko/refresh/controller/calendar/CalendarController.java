package tko.refresh.controller.calendar;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tko.refresh.dto.calendar.response.calendar.CalendarResDto;
import tko.refresh.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import tko.refresh.dto.calendar.request.calendar.CalendarReqDto;

@RestController
@RequestMapping("/calendar")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
    public ResponseEntity getCalendar(@ModelAttribute @Valid CalendarReqDto dto) {
        return ResponseEntity.ok().body(calendarService.updateCalendar(dto));
    }
}
