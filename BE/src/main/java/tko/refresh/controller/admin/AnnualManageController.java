package tko.refresh.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.service.admin.AnnualManageService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/annual")
public class AnnualManageController {

    private final AnnualManageService annualManageService;

    @GetMapping("/{page}")
    public ResponseEntity getAnnualAllList(@PathVariable int page){
        List<AnnualManageDto> list = annualManageService.getAnnualManageAllList();
        return ResponseEntity.ok().body(list);
    }


}
