package com.project.orderflow.admin.domain;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component // 이 애노테이션을 추가하여 스프링 빈으로 등록합니다.
public class Phone {
    private  String phoneNumber;
    private  String rand;


}