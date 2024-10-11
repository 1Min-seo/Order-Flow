package com.project.orderflow.customer.service;

import com.project.orderflow.admin.domain.Food;
import com.project.orderflow.admin.repository.FoodRepository;
import com.project.orderflow.customer.domain.Order;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import com.project.orderflow.customer.domain.enums.PaymentMethod;
import com.project.orderflow.customer.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;

    public OrderService(OrderRepository orderRepository, FoodRepository foodRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
    }

    // 주문 및 결제 처리
    public Order placeOrder(Long ownerId, String foodName, Integer quantity, PaymentMethod paymentMethod, Long tableId) {
        Food food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new IllegalArgumentException("해당 음식이 없습니다."));

        Integer totalAmount = food.getPrice() * quantity;

        Order order = Order.builder()
                .foodName(foodName)
                .quantity(quantity)
                .ownerId(ownerId)
                .food(food)
                .paymentMethod(paymentMethod)
                .totalAmount(totalAmount)
                .tableId(tableId)
                .orderStatus(OrderStatus.PENDING) // 주문 상태 기본값 비완료
                .orderTime(LocalDateTime.now()) // 주문 시간 설정
                .build();

        return orderRepository.save(order);
    }

    // 주문 상태 변경 (비완료 -> 완료)
    public Order completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        order.setOrderStatus(OrderStatus.COMPLETED); // 상태 완료로 변경
        return orderRepository.save(order);
    }

    // 주문내역 보기 (ownerId로 구분)
    public List<Order> getOrderHistory(Long ownerId) {
        return orderRepository.findByOwnerId(ownerId);
    }
}
