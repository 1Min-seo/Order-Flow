package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.dto.EmailCheckDto;
import com.project.orderflow.admin.dto.SignUpDto;
import com.project.orderflow.admin.service.MailSendService;
import com.project.orderflow.admin.service.OwnerService;
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
@Slf4j
public class AuthRestController {
    private final OwnerService ownerService;
    private final MailSendService mailSendService;
    private final BasicErrorController basicErrorController;

    //메일 인증 요청
    @PostMapping("/email/send")
    public ResponseEntity<?> mailAuthCheck(@RequestBody EmailCheckDto emailCheckDto){
        log.info("이메일 인증 코드 요청");
        try{
            String verifyCode = mailSendService.joinEmail(emailCheckDto.getEmail());
            log.info("이메일 인증 코드 전송 성공: "+verifyCode);

            return ResponseEntity.ok(Map.of(
                    "message", "인증 이메일이 성공적으로 발송되었습니다.",
                    "verifyCode", verifyCode
            ));
        }catch(Exception e){
            log.error("이메일 인증 코드 전송 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "인증 이메일 발송을 실패하였습니다."));
        } 

    }

    //회원가입 요청
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
}
