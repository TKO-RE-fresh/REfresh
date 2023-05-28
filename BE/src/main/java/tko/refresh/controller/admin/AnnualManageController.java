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
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/annual")
public class AnnualManageController {

    private final AnnualManageService annualManageService;

    @GetMapping("/{page}")
    public ResponseEntity getAnnualAllList(@PathVariable Optional<Integer> page){
        int formatPage = page.orElse(0);

        List<AnnualManageDto> list = annualManageService.getAnnualManageAllList(formatPage);

        return ResponseEntity.ok().body(list);
    }




}
