package com.project.orderflow.customer.service;

import com.project.orderflow.admin.domain.Food;
import com.project.orderflow.admin.repository.FoodRepository;
import com.project.orderflow.customer.domain.Order;
import com.project.orderflow.customer.domain.enums.PaymentMethod;
import com.project.orderflow.customer.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;

    public OrderService(OrderRepository orderRepository, FoodRepository foodRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
    }

    // 일반 주문 및 결제 처리
    public Order placeOrder(Long ownerId, String foodName, Integer quantity, PaymentMethod paymentMethod, Long tableId) { // option 삭제
        Food food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new IllegalArgumentException("해당 음식이 없습니다."));

        // 총 결제 금액 계산 (음식 가격 * 수량)
        Integer totalAmount = food.getPrice() * quantity;

        // 주문 생성 및 저장
        Order order = Order.builder()
                .foodName(foodName)
                .quantity(quantity)
                .ownerId(ownerId)
                .food(food)
                .paymentMethod(paymentMethod)
                .totalAmount(totalAmount)
                .tableId(tableId) // 테이블 ID 저장
                .build();

        return orderRepository.save(order);
    }

    // 주문내역 보기 (ownerId로 구분)
    public List<Order> getOrderHistory(Long ownerId) {
        return orderRepository.findByOwnerId(ownerId);
    }
}
