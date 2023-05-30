package tko.refresh.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.dto.admin.AnnualSearchDto;
import tko.refresh.service.admin.AnnualManageService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/annual")
public class AnnualManageController {

    private final AnnualManageService annualManageService;

    @GetMapping("/{page}")
    public ResponseEntity getSearchList(@RequestParam(required = false) AnnualSearchDto searchDto,
                                        @PathVariable Optional<Integer> page){
        int formatPage = page.orElse(1);
        List<AnnualManageDto> list;

        if (searchDto == null) {
           list = annualManageService.getAnnualManageAllList(formatPage);
        }else{
            list=annualManageService.getSearchAnnualMangeList(searchDto,formatPage);
        }

        return ResponseEntity.ok().body(list);
    }


   @PutMapping("/{status}")
    public ResponseEntity processAnnualRequest(@RequestBody String uid, @PathVariable String status){
       return ResponseEntity.ok().body("dd");
   }

}
