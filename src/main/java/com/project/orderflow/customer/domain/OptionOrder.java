package com.project.orderflow.customer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OptionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false, referencedColumnName = "id")
    private ItemOption itemOption;

    @NotNull
    private Long ownerId;

    @NotNull
    private Integer quantity;

    @NotNull
    private Long tableId; // 테이블 ID 추가

    @Builder
    public OptionOrder(ItemOption itemOption, Long ownerId, Integer quantity, Long tableId) { // tableId 추가
        this.itemOption = itemOption;
        this.ownerId = ownerId;
        this.quantity = quantity;
        this.tableId = tableId; // tableId 저장
    }
}