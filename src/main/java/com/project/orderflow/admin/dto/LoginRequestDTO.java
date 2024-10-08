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
public class LoginRequestDTO {

    private String email;
    private String password;

}
