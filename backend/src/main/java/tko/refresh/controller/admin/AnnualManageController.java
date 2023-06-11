package tko.refresh.controller.admin;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.dto.admin.AnnualSearchDto;
import tko.refresh.dto.admin.AnnualStatusDto;
import tko.refresh.service.admin.AnnualManageService;
=======
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.dto.admin.AnnualSearchDto;
import tko.refresh.service.admin.AnnualManageService;

import java.util.List;
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/annual")
<<<<<<< HEAD
@CrossOrigin(origins="http://localhost:8080")
=======
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
public class AnnualManageController {

    private final AnnualManageService annualManageService;

    @GetMapping("/{page}")
<<<<<<< HEAD
    public ResponseEntity getSearchList(@ModelAttribute AnnualSearchDto searchDto,
                                        @PathVariable Optional<Integer> page){
        Page<AnnualManageDto> list = annualManageService.getSearchAnnualMangeList(searchDto,page.orElse(1));
=======
    public ResponseEntity getSearchList(@RequestParam(required = false) AnnualSearchDto searchDto,
                                        @PathVariable Optional<Integer> page){
        int formatPage = page.orElse(0);
        List<AnnualManageDto> list;

        if (searchDto == null) {
           list = annualManageService.getAnnualManageAllList(formatPage);
        }else{
            list=annualManageService.getSearchAnnualMangeList(searchDto,formatPage);
        }
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
        return ResponseEntity.ok().body(list);
    }


<<<<<<< HEAD
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
=======
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
}
