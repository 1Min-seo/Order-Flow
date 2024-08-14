package com.project.orderflow.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDto {
    @Email
    @NotEmpty(message = "이메일일 입력해 주세요")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String password;
}
