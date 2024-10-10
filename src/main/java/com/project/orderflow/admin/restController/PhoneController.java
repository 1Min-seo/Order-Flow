package com.project.orderflow.admin.restController;


import com.project.orderflow.admin.domain.Phone;
import com.project.orderflow.admin.dto.EmailRequestDto;
import com.project.orderflow.admin.dto.PhoneRequestDto;
import com.project.orderflow.admin.service.PhoneService;
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
public class PhoneController {

    private final PhoneService phoneService;
    @Autowired
    Phone phones;

    @PostMapping("/phonecheck")
    public ResponseEntity<?> phonecheck1(@RequestBody PhoneRequestDto phoneRequestDto) { // 'String' 제거
        phoneService.sendOne(phoneRequestDto.getPhone());
        phones.setPhoneNumber(phoneRequestDto.getPhone());
        System.out.println(phones.getPhoneNumber() + "받아옴");
        return ResponseEntity.ok(Map.of(
                "message", "인증 번호가 성공적으로 발송되었습니다.",
                "verifyCode", phones.getRand()
        ));
    }


}
