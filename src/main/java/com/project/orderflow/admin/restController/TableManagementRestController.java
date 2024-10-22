package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.admin.dto.TableSetUpDto;
import com.project.orderflow.admin.service.OwnerService;
import com.project.orderflow.admin.service.TableManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "테이블 설정(관리자)",
            description = "테이블 개수를 설정합니다. {ownerId}는 JWT에서 추출된 점주 ID입니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "테이블 설정 성공"),
            @ApiResponse(responseCode = "404", description = "해당 점주를 찾을 수 없음", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content)
    })
    @PostMapping("/{ownerId}/setUp")
    public ResponseEntity<?> tableManage(@PathVariable(name = "ownerId") Long ownerId, @RequestBody TableSetUpDto tableSetUpDto) {
        Owner owner = ownerService.findOwnerById(ownerId);
        log.info("점주: " + owner.getId());
        log.info("지정 좌석 개수: " + tableSetUpDto.getNumberOfSeats());

        tableManagementService.setUpTables(owner, tableSetUpDto.getNumberOfSeats());
        log.info(String.format("테이블 %d개 셋업 완료", tableSetUpDto.getNumberOfSeats()));
        return ResponseEntity.ok("ok");
    }

    @Operation(
            summary = "테이블 생성(관리자)",
            description = "테이블을 생성합니다. {ownerId}는 JWT에서 추출된 점주 ID이며, x, y는 좌석의 좌표값입니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "테이블 생성 성공"),
            @ApiResponse(responseCode = "404", description = "해당 점주를 찾을 수 없음", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content)
    })
    @PostMapping("/{ownerId}/addSeat")
    public ResponseEntity<?> addSeat(@PathVariable(name = "ownerId") Long ownerId, @RequestParam Double x, @RequestParam Double y) {
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
            description = "{ownerId} JWT에서 추출한 점주 ID와 {seatId}는 해당 테이블의 ID입니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "테이블 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 좌석을 찾을 수 없음", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content)
    })
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
            description = "{ownerId} JWT에서 추출한 점주 ID를 이용하여 테이블 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "테이블 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Seat.class))),
            @ApiResponse(responseCode = "204", description = "테이블 없음", content = @Content)
    })
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
            description = "{ownerId} JWT에서 추출한 점주 ID와 {seatId}는 이동할 테이블의 ID입니다. x, y는 좌표값입니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "테이블 이동 성공"),
            @ApiResponse(responseCode = "404", description = "해당 좌석을 찾을 수 없음", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content)
    })
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
