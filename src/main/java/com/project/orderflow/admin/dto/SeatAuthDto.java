package com.project.orderflow.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SeatAuthDto {
    @NotBlank(message = "인증 코드를 입력 해주세요")
    private String authCode;

    public SeatAuthDto(String authCode){
        this.authCode=authCode;
    }
}
