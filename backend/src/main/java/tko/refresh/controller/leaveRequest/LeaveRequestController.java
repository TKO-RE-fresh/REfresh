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
        System.out.println("디티오 들어오나: "+ leaveRequestDto);
        System.out.println("컨트롤러 시작일: " +leaveRequestDto.getPeriod().getStartDate() + "/타입: "+ leaveRequestDto.getPeriod().getStartDate().getClass().getName());
        System.out.println("컨트롤러 종료일: " +leaveRequestDto.getPeriod().getEndDate()+ "/타입: "+ leaveRequestDto.getPeriod().getEndDate().getClass().getName());

        System.out.println("시간 확인: " + new Date());

        GlobalResponseDto leaveRequest = leaveRequestService.createLeaveRequest(leaveRequestDto);
        System.out.println("연차 관련 정보: "+leaveRequest.getMessage());
        System.out.println("return 할 ResponseEntity: " + ResponseEntity.ok().body(leaveRequest));
//        System.out.println("헤더: " +ResponseEntity.ok().body(leaveRequest).getHeaders());
//        System.out.println("바디: " +ResponseEntity.ok().body(leaveRequest).getBody());
//        System.out.println("바디의 tostring index(1) " +ResponseEntity.ok().body(leaveRequest).getBody());
//        System.out.println("상태코드: " +ResponseEntity.ok().body(leaveRequest).getStatusCode());

        return ResponseEntity.ok().body(leaveRequest);
    }
}