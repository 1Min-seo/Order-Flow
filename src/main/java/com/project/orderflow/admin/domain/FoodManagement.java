package com.project.orderflow.admin.domain;//package com.project.orderflow.admin.domain;
//
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter @Setter
//@NoArgsConstructor
//public class FoodManagement {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name="owner_id")
//    private Owner owner;
//
//    @OneToMany(mappedBy = "foodManagement", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Food> foods=new ArrayList<>();
//
//    @OneToOne
//    @JoinColumn(name="category_id")
//    private Category category;
//
//    @Builder
//    public FoodManagement(Owner owner){
//        this.owner=owner;
//        owner.setFoodManagement(this);
//    }
//}