package tko.refresh.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.dto.admin.AnnualSearchDto;
import tko.refresh.dto.admin.AnnualStatusDto;
import tko.refresh.service.admin.AnnualManageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/annual")
@CrossOrigin("*")
public class AnnualManageController {

    private final AnnualManageService annualManageService;

    @GetMapping("/{page}")
    public ResponseEntity getSearchList(@ModelAttribute AnnualSearchDto searchDto,
                                        @PathVariable Optional<Integer> page){
        int formatPage = page.orElse(1);
        Page<AnnualManageDto> list;

        list=annualManageService.getSearchAnnualMangeList(searchDto,formatPage);

        return ResponseEntity.ok().body(list);
    }


   @PutMapping
    public ResponseEntity processAnnualRequest(@RequestBody AnnualStatusDto statusDto){
        Boolean result = true;
        System.out.println(statusDto);

       if(statusDto.getStatus().equals(AnnualStatus.AGREE.getCode())){
           result = annualManageService.AccessAnnualRequest(statusDto.getUid());
       }else if(statusDto.getStatus().equals(AnnualStatus.REJECT.getCode())) {
          result = annualManageService.RejectAnnualRequest(statusDto.getUid(),statusDto.getRejectReason());
       }

       if(result){
           return ResponseEntity.ok("Success");
       }else {
           return ResponseEntity.badRequest().build();
       }
   }
}
