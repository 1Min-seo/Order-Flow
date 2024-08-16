package com.project.orderflow.admin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String businessNumber;


    @OneToMany(mappedBy = "owner")
    private List<TableOrder> tableOrders;

    @Builder
    public Owner(String email, String name, String passwordHash, String businessNumber) {
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
        this.businessNumber = businessNumber;

    }

}
