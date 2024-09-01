package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.dto.OrderMenuReqDto;
import com.project.orderflow.customer.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Cart API")
public class CartController {
    private final CartService cartService;

    /**
     * 장바구니 항목 추가
     */
    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestParam String tableNum, @RequestBody List<OrderMenuReqDto> orderMenuReqDto) {
        cartService.addMenusToCart(tableNum, orderMenuReqDto);
        return ResponseEntity.ok().build();
    }

}
