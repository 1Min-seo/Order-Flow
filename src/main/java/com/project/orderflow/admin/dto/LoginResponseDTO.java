package com.project.orderflow.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "응답 상태", example = "Success")
    private String status;

    @Schema(description = "응답 메시지", example = "로그인 성공")
    private String message;

    @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String token;  // JWT 토큰을 저장할 필드 추가

    // 두 개의 매개변수를 받는 생성자
    public LoginResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
