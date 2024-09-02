package com.project.orderflow.admin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "table_management_id", nullable = false)
    private TableManagement tableManagement;

    @Builder
    public Food(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
