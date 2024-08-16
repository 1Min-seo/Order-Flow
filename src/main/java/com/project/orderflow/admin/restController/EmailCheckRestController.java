//package com.project.orderflow.admin.restController;
//
//import com.project.orderflow.admin.dto.EmailCheckDto;
//import com.project.orderflow.admin.dto.EmailRequestDto;
//import com.project.orderflow.admin.service.MailSendService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class EmailCheckRestController {
//    private final MailSendService mailSendService;
//
//    @PostMapping("/mailSend")
//    public ResponseEntity<?> mailSend(@RequestBody EmailRequestDto emailDto){
//        log.info("이메일 인증 요청이 들어옴");
//        log.info("인증 이메일: " + emailDto.getEmail());
//        try {
//            String verificationCode = mailSendService.joinEmail(emailDto.getEmail());
//            log.info("인증번호: " + verificationCode);
//
//            return ResponseEntity.ok(Map.of(
//                    "message", "인증 이메일이 성공적으로 발송되었습니다.",
//                    "verificationCode", verificationCode
//            ));
//        } catch (Exception e) {
//            log.error("이메일 전송 실패: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("message", "이메일 전송에 실패했습니다."));
//        }
//    }
//
//    @PostMapping("/mailAuthCheck")
//    public ResponseEntity<?> authCheck(@RequestBody EmailCheckDto emailCheckDto) {
//        log.info("이메일 인증 체킹");
//
//        Boolean checked = mailSendService.checkAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
//        if (checked) {
//            log.info("인증 성공");
//            return ResponseEntity.ok(Map.of(
//                    "message", "인증 번호가 일치합니다."
//            ));
//        } else {
//            log.info("인증 실패");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("message", "인증 번호가 일치하지 않습니다."));
//        }
//    }
//}
