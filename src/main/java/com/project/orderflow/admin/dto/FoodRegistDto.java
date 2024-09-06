package com.project.orderflow.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FoodRegistDto {

    @NotNull(message = "음식 이름을 작성해 주세요")
    private String name;

    private String description;

    @NotNull(message = "음식 가격을 작성해 주세요")
    private int price;


}
