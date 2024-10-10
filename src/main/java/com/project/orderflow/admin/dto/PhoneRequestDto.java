package com.project.orderflow.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PhoneRequestDto {
    @Email // 이메일 주소의 형식 검증
    @NotEmpty(message = "핸드폰 번호를 입력해주세요")
    private String phone;

}