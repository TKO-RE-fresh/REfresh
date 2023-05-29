package tko.refresh.controller.holiday;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/holiday")
public class HolidayController {
    @GetMapping
    public String get() {
        return "success";
    }
}
