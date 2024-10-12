package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.LoginDto;
import com.project.orderflow.admin.service.OwnerService;
import com.project.orderflow.Jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.orderflow.admin.dto.LoginDto;
import com.project.orderflow.admin.dto.LoginResponseDTO;
import com.project.orderflow.admin.dto.LoginRequestDTO;

import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "로그인(관리자)", description = "JWT권한 없음. JWT토큰 발급")
public class LoginRestController {

    private final OwnerService ownerService;

    @Autowired
    private JwtUtil jwtUtil;


    @Operation(
            summary = "로그인(JWT 권한 없음)",
            description = "emial과 비밀번호 입력"
    )
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        log.info("로그인 요청");

        // 사용자 인증
        LoginResponseDTO response = ownerService.authenticateUser(loginRequest);

        if ("Success".equals(response.getStatus())) {
            // 로그인 성공 시 Owner 정보를 통해 JWT 토큰 생성
            Optional<Owner> owner = Optional.ofNullable(ownerService.findOwnerByEmail(loginRequest.getEmail()));
            if (owner.isPresent()) {
                // 이메일과 id를 함께 사용하여 JWT 토큰 생성
                String jwt = jwtUtil.generateToken(owner.get().getEmail(), owner.get().getId());
                response.setToken(jwt);  // JWT 토큰을 응답에 추가
            }
        }

        return response;
    }

}