package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.*;
import com.project.orderflow.admin.repository.OwnerRepository;
import com.project.orderflow.admin.service.MailSendService;
import com.project.orderflow.admin.service.OwnerService;
//import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "회원가입 및 관리(관리자)", description = "테이블 연결")
@Slf4j
public class AuthRestController {
    private final OwnerService ownerService;
    private final MailSendService mailSendService;
    private final BasicErrorController basicErrorController;
    private final OwnerRepository ownerRepository;

    //회원가입 요청
    @Operation(
            summary = "회원가입 신청(JWT권한없음)",
            description = "email: 아이디 verifyCode: 핸드폰 인증번호"
    )
    @PostMapping("/signUp/send")
    public ResponseEntity<?> signUpCheck(@RequestBody SignUpDto signUpDto){
        log.info("회원가입 요청");
        boolean isVerified= mailSendService.checkAuthNum(signUpDto.getEmail(), signUpDto.getVerifyCode());

        if (signUpDto.getVerifyCode().isEmpty()) {
            log.info("인증 코드 null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "인증코드를 입력해 주세요."));
        }

        if(!isVerified){
            log.info("이메일 인증 코드 불일치");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "인증코드가 일치하지 않습니다."));
        }
        log.info("이메일 인증 코드 일치");
        ownerService.registerOwner(signUpDto);
        log.info("owner 회원 가입 성공");
        return ResponseEntity.ok(Map.of("message", "회원가입 성공!"));

    }

    // 정보 수정
    // 사업자번호 수정, 비밀번호 수정
    //회원가입 요청
    @Operation(
            summary = "회원정보 수정",
            description = "onwerId: JWT Id추출 값"
    )
    @PostMapping("/{ownerId}/updateInfo")
    public ResponseEntity<?> updateInfo(@PathVariable Long ownerId, @RequestBody InfoUpdateDto infoUpdateDto){
        log.info("정보 수정 요청");
        try{
            ownerService.updateOwnerInfo(ownerId, infoUpdateDto);
            return ResponseEntity.ok(Map.of("message","정보 수정 성공"));
        }catch (Exception e){
            log.error("정보 수정 실패",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message","정보 수정 중 오류 발생"));
        }
    }

    @Operation(
            summary = "회원정보 삭제",
            description = "onwerId: JWT Id추출 값"
    )
    @DeleteMapping("{ownerId}/deleteInfo")
    public ResponseEntity<?> deleteInfo(@PathVariable Long ownerId){
        Owner owner =ownerService.findOwnerById(ownerId);
        if(owner == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "회원 찾을 수 없음"));
        }

        ownerRepository.delete(owner);
        return ResponseEntity.ok(Map.of("message","회원삭제 성공!!"));
    }
}
