package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.TableSetUpDto;
import com.project.orderflow.admin.service.OwnerService;
import com.project.orderflow.admin.service.TableManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/table/manage")
public class TableManagementRestController {
    private final OwnerService ownerService;
    private final TableManagementService tableManagementService;

    @PostMapping("/setup/{ownerId}")
    public ResponseEntity<?> tableManage(@PathVariable(name="ownerId") Long ownerId, @RequestBody TableSetUpDto tableSetUpDto){
        Owner owner = ownerService.findOwnerById(ownerId);
        tableManagementService.setUpTables(owner, tableSetUpDto.getNumberOfSeats());

        log.info(String.format("테이블 %d개 셋업 완료", tableSetUpDto.getNumberOfSeats()));
        return ResponseEntity.ok("ok");
    }
}
