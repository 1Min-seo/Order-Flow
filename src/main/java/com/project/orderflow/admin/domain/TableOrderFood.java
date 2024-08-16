package com.project.orderflow.admin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class TableOrderFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="table_order_id", nullable = false)
    private TableOrder tableOrder;

    @ManyToOne
    @JoinColumn(name="food_id", nullable = false)
    private Food food;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double totalPrice;

    @Builder
    public TableOrderFood(TableOrder tableOrder, Food food, int quantity, double totalPrice) {
        this.tableOrder = tableOrder;
        this.food = food;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    //수량변경
    public void updateQuantity(int newQuantity){
        this.quantity=newQuantity;
        this.totalPrice=this.food.getPrice()*newQuantity;
    }
}
