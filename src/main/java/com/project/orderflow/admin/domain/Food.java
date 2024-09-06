package com.project.orderflow.admin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "음식의 이름을 작성 해주세요")
    private String name;

    private String description;

    @NotBlank(message = "음식의 가격을 작성 해주세요")
    private int price;

    @ManyToOne
    @JoinColumn(name="food_management_id", nullable=false)
    private FoodManagement foodManagement;

    @Builder
    public Food(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
