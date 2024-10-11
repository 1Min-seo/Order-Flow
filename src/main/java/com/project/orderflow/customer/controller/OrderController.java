package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.Order;
import com.project.orderflow.customer.domain.enums.PaymentMethod;
import com.project.orderflow.customer.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "음식주문", description = "주문(고객)/주문내역조회(관리자)")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "음식주문(고객)",
            description = "ownerId에는 JWT토큰에서 추출한 Id값을 넣어주시고, foodName에는 해당 음식의 이름 paymentMethod는 CASG CARD KAKAOPAY중 하나 넣어주시고 tableId는 해당 테이블의 id값 넣어주시면 됩니다!"
    )
    // 일반 주문 및 결제 처리 (옵션 주문은 별도로 처리)
    @PostMapping("/place-order")
    public ResponseEntity<Order> placeOrder(
            @RequestParam Long ownerId,
            @RequestParam String foodName,
            @RequestParam Integer quantity,
            @RequestParam PaymentMethod paymentMethod,
            @RequestParam Long tableId // 테이블 ID 추가
    ) {
        Order order = orderService.placeOrder(ownerId, foodName, quantity, paymentMethod, tableId); // option 파라미터 삭제
        return ResponseEntity.ok(order);
    }

    @Operation(
            summary = "음식주문내역 조회(관리자)",
            description = "ownerId에는 JWT토큰에서 추출한 Id값을 넣어"
    )
    // 주문내역 보기
    @GetMapping("/history/{ownerId}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long ownerId) {
        List<Order> orders = orderService.getOrderHistory(ownerId);
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = "주문상태 관리(관리자)",
            description = "비완료 -> 완료"
    )
    // 주문 상태 변경 (비완료 -> 완료)
    @PutMapping("/complete-order/{orderId}")
    public ResponseEntity<Order> completeOrder(@PathVariable Long orderId) {
        Order order = orderService.completeOrder(orderId);
        return ResponseEntity.ok(order);
    }
}

