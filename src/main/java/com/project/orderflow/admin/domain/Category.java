//package com.project.orderflow.admin.domain;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@NoArgsConstructor
//@Getter
//public class Category {
//    @Id
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, unique = true)
//    private CategoryType name;
//
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Food> foods = new ArrayList<>();
//}
