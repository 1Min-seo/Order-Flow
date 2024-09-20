package com.project.orderflow.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter @Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private CategoryType categoryType;

    @OneToOne(mappedBy = "category")
    private FoodManagement foodManagement;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foods;

    @Column(nullable = false)
    private Boolean isActive = true;

    public CategoryType findByCategoryType(CategoryType categoryType) {
        return Arrays.stream(CategoryType.values())
                .filter(e -> e.equals(categoryType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("카테고리 타입 찾을 수 없음"));
    }

}
