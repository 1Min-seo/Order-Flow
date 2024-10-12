package com.project.orderflow.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FoodDto {
    private String name;
    private String categoryName;
    private Integer price;
    private String description;
    private String imageUrl;
}