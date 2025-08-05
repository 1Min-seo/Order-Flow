package com.project.orderflow.admin.restController;


import com.project.orderflow.admin.domain.Phone;
import com.project.orderflow.admin.dto.EmailRequestDto;
import com.project.orderflow.admin.dto.PhoneRequestDto;
import com.project.orderflow.admin.service.PhoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "핸드폰 인증", description = "JWT권한 없음. -없이 번호만 입력")
public class PhoneController {

    private final PhoneService phoneService;
    @Autowired
    Phone phones;

    @PostMapping("/phonecheck")
    public ResponseEntity<?> phonecheck1(@RequestBody PhoneRequestDto phoneRequestDto) {
        phoneService.sendOne(phoneRequestDto.getPhone());
        phones.setPhoneNumber(phoneRequestDto.getPhone());
        System.out.println(phones.getPhoneNumber() + "받아옴");
        return ResponseEntity.ok(Map.of(
                "message", "인증 번호가 성공적으로 발송되었습니다.",
                "verifyCode", phones.getRand()
        ));
    }


}
