package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.admin.dto.TableSetUpDto;
import com.project.orderflow.admin.service.OwnerService;
import com.project.orderflow.admin.service.TableManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "테이블 관리", description = "테이블 관리")
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

    @Operation(
            summary = "테이블 생성(관리자)",
            description = "테이블 생성(관리자) JWT토큰에 추출된 id값과 테이블의 x,y값"
    )
    @PostMapping("/{ownerId}/addSeat")
    public ResponseEntity<?> addSeat(@PathVariable(name="ownerId") Long ownerId, @RequestParam Double x, @RequestParam Double y) {
        Owner owner = ownerService.findOwnerById(ownerId);

        if (owner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 점주를 찾을 수 없습니다.");
        }

        tableManagementService.addSeat(owner, x, y);

        log.info("좌석 추가: x={}, y={}", x, y);
        return ResponseEntity.ok("좌석 추가 완료");
    }

    @Operation(
            summary = "테이블 삭제(관리자)",
            description = "JWT토큰에 추출된 id값과 해당 테이블의 id값"
    )
    // 좌석 삭제 API
    @DeleteMapping("/{ownerId}/delete-seat/{seatId}")
    public ResponseEntity<?> deleteSeat(@PathVariable Long ownerId, @PathVariable Long seatId) {
        try {
            tableManagementService.deleteSeat(ownerId, seatId);
            return ResponseEntity.ok("좌석 삭제 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좌석 삭제 중 오류가 발생했습니다.");
        }
    }



    @Operation(
            summary = "테이블 조회(관리자)",
            description = "JWT토큰에 추출된 id값"
    )
    @GetMapping("/{ownerId}/seats")
    public ResponseEntity<?> getSeatsByOwnerId(@PathVariable(name = "ownerId") Long ownerId) {
        Owner owner = ownerService.findOwnerById(ownerId);
        List<Seat> seats = tableManagementService.getSeatsByOwner(owner);

        if (seats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(seats);
    }

    @Operation(
            summary = "테이블 이동(관리자)",
            description = "JWT토큰에 추출된 id값, 해당 테이블 id값 움직인 좌표 값 x,y"
    )
    // 테이블 이동 시 좌석의 x, y 좌표 변경 API
    @PutMapping("/{ownerId}/move-seat/{seatId}")
    public ResponseEntity<?> moveSeat(@PathVariable Long ownerId, @PathVariable Long seatId, @RequestParam Double x, @RequestParam Double y) {
        try {
            tableManagementService.moveSeat(ownerId, seatId, x, y);
            return ResponseEntity.ok("좌석 이동 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좌석 이동 중 오류가 발생했습니다.");
        }
    }

}

