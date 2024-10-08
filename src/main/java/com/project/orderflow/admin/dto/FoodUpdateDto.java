package com.project.orderflow.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class FoodUpdateDto {
    private String name;
    private MultipartFile image;
    private String description;
    private Integer price;
    private String categoryName;

}
