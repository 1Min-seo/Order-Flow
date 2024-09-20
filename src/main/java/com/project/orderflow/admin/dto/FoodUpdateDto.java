package com.project.orderflow.admin.dto;

import lombok.Getter;

@Getter
public class FoodUpdateDto {
    private Long id;
    private String name;
    private String description;
    private int price;
}
