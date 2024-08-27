package com.project.orderflow.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SeatAuthDto {

    @NotBlank(message = "테이블 넘버를 입력 해주세요")
    private String tableNumber;

    @NotBlank(message = "인증 코드를 입력 해주세요")
    private String authCode;

    public SeatAuthDto(String tableNumber, String authCode){
        this.tableNumber=tableNumber;
        this.authCode=authCode;
    }
}
