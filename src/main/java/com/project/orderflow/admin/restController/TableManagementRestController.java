package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.admin.dto.TableSetUpDto;
import com.project.orderflow.admin.service.OwnerService;
import com.project.orderflow.admin.service.TableManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/table/manage")
public class TableManagementRestController {
    private final OwnerService ownerService;
    private final TableManagementService tableManagementService;

     
    @PostMapping("/{ownerId}/setUp")
    public ResponseEntity<?> tableManage(@PathVariable(name="ownerId") Long ownerId, @RequestBody TableSetUpDto tableSetUpDto){
        Owner owner = ownerService.findOwnerById(ownerId);
        log.info("점주: " + owner.getId());
        log.info("지정 좌석 개수: " + tableSetUpDto.getNumberOfSeats());

        tableManagementService.setUpTables(owner, tableSetUpDto.getNumberOfSeats());
        log.info(String.format("테이블 %d개 셋업 완료", tableSetUpDto.getNumberOfSeats()));
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/{ownerId}/addSeats")
    public ResponseEntity<?> addSeats(@PathVariable(name="ownerId") Long ownerId, @RequestParam int seatsToAdd){
        Owner owner=ownerService.findOwnerById(ownerId);
        tableManagementService.addSeats(owner, seatsToAdd);

        log.info("좌석 {}개 추가", seatsToAdd);
        return ResponseEntity.ok("좌석 추가 완료");
    }

    @PostMapping("/{ownerId}/removeSeats")
    public ResponseEntity<?> removeSeats(@PathVariable(name="ownerId") Long ownerId, @RequestParam int seatsToRemove){
        Owner owner=ownerService.findOwnerById(ownerId);
        tableManagementService.deleteSeats(owner, seatsToRemove);

        log.info("좌석 {}개 삭제", seatsToRemove);
        return ResponseEntity.ok("좌석 삭제 완료");
    }


    @GetMapping("/{ownerId}/seats")
    public ResponseEntity<?> getSeatsByOwnerId(@PathVariable(name = "ownerId") Long ownerId) {
        Owner owner = ownerService.findOwnerById(ownerId);
        List<Seat> seats = tableManagementService.getSeatsByOwner(owner);

        if (seats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(seats);
    }

}
