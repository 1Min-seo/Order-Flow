package com.project.orderflow.admin.dto;

import com.project.orderflow.admin.domain.Category;
import com.project.orderflow.admin.domain.CategoryType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FoodRegistDto {

    @NotBlank(message = "음식 이름을 작성해 주세요")
    private String name;

    private String description;

    @NotNull
    @Min(0)
    private Integer price;

    @NotNull
    private CategoryType categoryType;

}
