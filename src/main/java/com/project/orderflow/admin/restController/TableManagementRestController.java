package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.TableSetUpDto;
import com.project.orderflow.admin.service.OwnerService;
import com.project.orderflow.admin.service.TableManagementService;
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

    @PostMapping("/addSeats/{ownerId}")
    public ResponseEntity<?> addSeats(@PathVariable(name="ownerId") Long ownerId, @RequestParam int seatsToAdd){
        Owner owner=ownerService.findOwnerById(ownerId);
        tableManagementService.addSeats(owner, seatsToAdd);

        log.info("좌석 {}개 추가", seatsToAdd);
        return ResponseEntity.ok("좌석 추가 완료");
    }

    @PostMapping("/removeSeats/{ownerId}")
    public ResponseEntity<?> removeSeats(@PathVariable(name="ownerId") Long ownerId, @RequestParam int seatsToRemove){
        Owner owner=ownerService.findOwnerById(ownerId);
        tableManagementService.deleteSeats(owner, seatsToRemove);

        log.info("좌석 {}개 삭제", seatsToRemove);
        return ResponseEntity.ok("좌석 삭제 완료");
    }
}
