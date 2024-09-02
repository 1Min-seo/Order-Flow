package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.LoginDto;
import com.project.orderflow.admin.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginRestController {
    private final OwnerService ownerService;

    @PostMapping("/login")
    public ResponseEntity<?> loginOwner(@RequestBody LoginDto loginDto){
        log.info("로그인 요청");
        Boolean isLoginSuccessful= ownerService.loginOwner(loginDto);

        if(isLoginSuccessful){
            log.info("로그인 성공");
            return ResponseEntity.ok(
                    Map.of("message", "로그인 성공")
            );
        }else{
            log.info("로그인 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "로그인 실패"));
        }
    }

}
