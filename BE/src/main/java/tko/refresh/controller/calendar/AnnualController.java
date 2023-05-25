package tko.refresh.controller.calendar;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;

@RestController
@RequestMapping("/calendar/annual")
public class AnnualController {

    @GetMapping
    public ResponseEntity getAnnualByDate() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/member")
    public ResponseEntity getAnnualByMemberName() {


        return ResponseEntity.ok().build();
    }

    @GetMapping("/department")
    public ResponseEntity getAnnualByDepartmentName() {
        return ResponseEntity.ok().build();
    }

}
