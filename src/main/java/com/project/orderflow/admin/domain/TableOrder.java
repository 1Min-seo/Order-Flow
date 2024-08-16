package com.project.orderflow.admin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class TableOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition="BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String tableNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name="owner_id", nullable=false)
    private Owner owner;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderAt;

    @OneToMany(mappedBy = "tableOrder", cascade = CascadeType.ALL)
    private List<TableOrderFood> orderItems;

    @Builder
    public TableOrder(String tableNumber, Owner owner) {
        this.tableNumber = tableNumber;
        this.status = OrderStatus.ORDER_COMPLETED;
        this.owner = owner;
    }

    @PrePersist
    protected void onCreate() {
        this.orderAt=LocalDateTime.now();
    }


}
