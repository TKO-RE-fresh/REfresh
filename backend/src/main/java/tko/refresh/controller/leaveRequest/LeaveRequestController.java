package tko.refresh.controller.leaveRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tko.refresh.dto.GlobalResponseDto;
import tko.refresh.dto.leaveRequest.LeaveRequestDto;
import tko.refresh.service.leaveRequest.LeaveRequestService;

import java.util.Date;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/leaveRequest")
@CrossOrigin(origins = "*")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    /* 연차 신청 정보 - 이름 & 사용한 연차/남은 연차 */
    @GetMapping
    public ResponseEntity getLeaveInfo(){
        String memberId = "jaeseok";
        Map<String, Double> usedAnnualLeave = leaveRequestService.getUsedAnnualLeave(memberId);
        System.out.println("사용한 연차개수: " +usedAnnualLeave);
        return ResponseEntity.ok().body(usedAnnualLeave);
    }


    /* 연차 신청폼에 들어온 정보를 토대로 연차 신청  */
    @PostMapping
    public ResponseEntity requestForLeave(@RequestBody LeaveRequestDto leaveRequestDto) {
        System.out.println("leaveRequestDto = " + leaveRequestDto.getPeriod().getStartDate() + " " + leaveRequestDto.getPeriod().getEndDate());
        GlobalResponseDto leaveRequest = leaveRequestService.createLeaveRequest(leaveRequestDto);


        return ResponseEntity.ok().body(leaveRequest);
    }
}