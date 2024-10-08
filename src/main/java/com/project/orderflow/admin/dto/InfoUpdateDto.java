package com.project.orderflow.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InfoUpdateDto {
    private String businessNumber;
    private String newPassword;
    private String newPasswordConfirm;
}
