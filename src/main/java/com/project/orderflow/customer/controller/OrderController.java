package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.dto.OrderReqDto;
import com.project.orderflow.customer.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private  final OrderService orderService;

    @GetMapping("/table/{orderId}")
    public ResponseEntity<OrderReqDto> getOrder(@PathVariable Long orderId) {
        OrderReqDto orderReqDto = orderService.getOrderById(orderId);
        if (orderReqDto != null) {
            return ResponseEntity.ok().body(orderReqDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/table")
    public ResponseEntity<?> orderMenu(@RequestBody OrderReqDto orderReqDto){
        orderService.order(orderReqDto);

        return ResponseEntity.ok().body(orderReqDto);
    }
}
