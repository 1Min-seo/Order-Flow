package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.Order;
import com.project.orderflow.customer.domain.enums.PaymentMethod;
import com.project.orderflow.customer.dto.FoodOrderDto;
import com.project.orderflow.customer.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
            description = "ownerId에는 JWT토큰에서 추출한 Id값을 넣어주시고, foodName에는 해당 음식의 이름, paymentMethod는 CASH, CARD, KAKAOPAY 중 하나를 넣어주시고, tableId는 해당 테이블의 id값을 넣어주시면 됩니다. 음식은 [{\"foodName\": \"순대\", \"quantity\": 2}, {\"foodName\": \"떡볶이\", \"quantity\": 2}, {\"foodName\": \"김밥\", \"quantity\": 2}] 형식으로 보내주세요."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "음식 주문 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content)
    })
    @PostMapping("/place-food-orders")
    public ResponseEntity<Void> placeFoodOrders(@RequestBody List<FoodOrderDto> foodOrders,
                                                @RequestParam Long ownerId,
                                                @RequestParam Long tableId,
                                                @RequestParam PaymentMethod paymentMethod) {
        orderService.placeFoodOrders(foodOrders, ownerId, tableId, paymentMethod);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "음식주문내역 조회(관리자)",
            description = "ownerId에는 JWT토큰에서 추출한 Id값"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 내역 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "주문 내역을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/history/{ownerId}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long ownerId) {
        List<Order> orders = orderService.getOrderHistory(ownerId);
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = "주문상태 관리(관리자)",
            description = "주문 상태를 비완료에서 완료로 변경합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 상태 완료로 변경 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음", content = @Content)
    })
    @PutMapping("/complete-order/{orderId}")
    public ResponseEntity<Order> completeOrder(@PathVariable Long orderId) {
        Order order = orderService.completeOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @Operation(
            summary = "주문 취소(관리자)",
            description = "orderId 값을 사용하여 주문을 취소합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "주문 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음", content = @Content)
    })
    @DeleteMapping("/delete-food-order/{orderId}")
    public ResponseEntity<Void> deleteFoodOrder(@PathVariable Long orderId) {
        orderService.deleteFoodOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}


