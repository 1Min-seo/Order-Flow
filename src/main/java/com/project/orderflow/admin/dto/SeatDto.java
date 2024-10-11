package com.project.orderflow.admin.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatDto {
    private Long id;
    private String authCode;
    private boolean isActive;
    private String tableNumber;
    private Long tableManagementId;
    private String qrUrl;
    private Double x;
    private Double y;
}
