package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.TableOrder;
import com.project.orderflow.customer.dto.OrderResDto;
import com.project.orderflow.customer.dto.TableOrderReqDto;
import com.project.orderflow.customer.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
public class OrderController {
    private  final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResDto> placeOrder(@RequestParam String tableNumber) {
        TableOrder tableOrder = orderService.order(tableNumber);
        OrderResDto response = new OrderResDto(
                "Order placed successfully",
                tableOrder.getId(),
                tableNumber,
                tableOrder.getTotalPrice());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/table/{tableNumber}")
    public List<TableOrderReqDto> getOrdersByTable(@PathVariable String tableNumber) {
        return orderService.getOrdersByTable(tableNumber);
    }
}
