package com.project.orderflow.admin.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignUpDto {
    @Email
    @NotEmpty(message = "이메일을 입력해 주세요")
    private String email;

    @NotEmpty(message = "이메일 인증번호를 입력해 주세요")
    private String verifyCode;

    @NotEmpty(message = "이름을 입력해 주세요")
    private String name;

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String password;

    @NotEmpty(message = "사업자 번호를 입력해 주세요")
    private String businessNumber;

}
