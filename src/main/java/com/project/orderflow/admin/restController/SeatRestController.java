package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.dto.SeatAuthDto;
import com.project.orderflow.admin.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/seats")
public class SeatRestController {
    private final SeatService seatService;

    @PostMapping("/activate")
    public ResponseEntity<?> activateSeat(@RequestBody SeatAuthDto seatAuthDto) {
        boolean isActivate = seatService.activateSeat(seatAuthDto.getTableNumber(), seatAuthDto.getAuthCode());
        if (!isActivate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "테이블 연결 실패"));
        }
        return ResponseEntity.ok(Map.of("message", "테이블 연결 성공"));
    }
}

