package com.project.orderflow.admin.restController;

import com.project.orderflow.Jwt.JwtUtil;
import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.admin.dto.LoginResponseDTO;
import com.project.orderflow.admin.dto.SeatAuthDto;
import com.project.orderflow.admin.repository.OwnerRepository;
import com.project.orderflow.admin.repository.SeatRepository;
import com.project.orderflow.admin.service.OwnerService;
import com.project.orderflow.admin.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/seats")
@Tag(name = "좌석연결(고객)", description = "JWT권한 없음. -없이 번호만 입력")
public class SeatRestController {
    private final SeatService seatService;
    private final OwnerService ownerService;
    private final SeatRepository seatRepository;

    @Autowired
    JwtUtil jwtUtil;



    @Operation(
            summary = "로그인(JWT 권한 없음)",
            description = "해당 테이블의 번호, 그리고 테이블 인증번호 필요."
    )
    @PostMapping("/activate")
    public ResponseEntity<?> activateSeat(@RequestBody SeatAuthDto seatAuthDto) {
        boolean isActivate = seatService.activateSeat(seatAuthDto.getAuthCode());
        // 사용자 인증
        if (!isActivate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "테이블 연결 실패"));
        }

        Optional<Seat> seat = seatRepository.findByAuthCode(seatAuthDto.getAuthCode());
        System.out.println("이거지: " + seat.get().getTableManagement().getId());
        String jwt = jwtUtil.generateToken(seat.get().getTableNumber(), seat.get().getTableManagement().getId() );
        System.out.println("토큰" + jwt);

        return ResponseEntity.ok(Map.of("message", "테이블 연결 성공",
                "Token:", jwt,
                "TableNumber:",seat.get().getTableNumber()));
    }
}

