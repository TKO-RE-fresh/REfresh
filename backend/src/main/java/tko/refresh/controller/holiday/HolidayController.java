package tko.refresh.controller.holiday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tko.refresh.service.calendar.HolidayService;

@RestController
@RequestMapping("/holiday")
public class HolidayController {
    @GetMapping
    public String get() {
        return "success";
    }
}
