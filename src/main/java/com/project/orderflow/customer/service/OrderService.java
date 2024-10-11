package com.project.orderflow.customer.service;

import com.project.orderflow.admin.domain.Food;
import com.project.orderflow.admin.repository.FoodRepository;
import com.project.orderflow.customer.domain.Order;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import com.project.orderflow.customer.domain.enums.PaymentMethod;
import com.project.orderflow.customer.dto.FoodOrderDto;
import com.project.orderflow.customer.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void placeFoodOrders(List<FoodOrderDto> foodOrders, Long ownerId, Long tableId, PaymentMethod paymentMethod) {
        for (FoodOrderDto foodOrder : foodOrders) {
            Food food = foodRepository.findByName(foodOrder.getFoodName())
                    .orElseThrow(() -> new IllegalArgumentException("해당 음식이 없습니다."));

            Integer totalAmount = food.getPrice() * foodOrder.getQuantity();

            Order order = Order.builder()
                    .foodName(foodOrder.getFoodName())
                    .quantity(foodOrder.getQuantity())
                    .ownerId(ownerId)
                    .food(food)
                    .paymentMethod(paymentMethod)
                    .totalAmount(totalAmount)
                    .tableId(tableId)
                    .orderStatus(OrderStatus.PENDING)
                    .orderTime(LocalDateTime.now())
                    .build();

            orderRepository.save(order);
        }
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

    // 음식 주문 삭제
    @Transactional
    public void deleteFoodOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("해당 주문이 존재하지 않습니다.");
        }
        orderRepository.deleteById(orderId);
    }
}
