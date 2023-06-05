package tko.refresh.controller.calendar;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
import tko.refresh.util.jwt.JwtAuthMember;
import tko.refresh.util.jwt.MemberDetailsImpl;
import tko.refresh.util.jwt.MemberDetailsServiceImpl;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    private final  JwtAuthMember jwtAuthMember;

    @GetMapping
    public ResponseEntity getCalendar(@ModelAttribute @Valid CalendarReqDto dto) {
        System.out.println("userDetails: " + jwtAuthMember.getJwtAuthMember());
        return ResponseEntity.ok().body(calendarService.updateCalendar(dto));
    }
}
