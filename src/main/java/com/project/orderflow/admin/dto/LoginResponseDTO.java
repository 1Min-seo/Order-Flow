package com.project.orderflow.admin.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginResponseDTO {

    private String status;
    private String message;
    private String token;  // JWT 토큰을 저장할 필드 추가


    // 두 개의 매개변수를 받는 생성자
    public LoginResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
}