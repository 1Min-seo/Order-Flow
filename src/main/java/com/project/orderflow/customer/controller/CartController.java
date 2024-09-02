package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.OrderMenu;
import com.project.orderflow.customer.dto.OrderMenuReqDto;
import com.project.orderflow.customer.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
     * 장바구니 조회
     */
    @GetMapping
    public ResponseEntity<List<OrderMenuReqDto>> getCartMenus(@RequestParam String tableNumber) {
        List<OrderMenuReqDto> cartMenus = cartService.getCartMenus(tableNumber);
        return ResponseEntity.ok(cartMenus);
    }

    /**
     * 장바구니 항목 추가
     */
    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestParam String tableNumber, @RequestBody List<OrderMenuReqDto> orderMenuReqDto) {
        cartService.addMenusToCart(tableNumber, orderMenuReqDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 장바구니 항목 수정
     */
    @PatchMapping("/{orderMenuId}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long orderMenuId,
                                            @RequestBody OrderMenuReqDto orderMenuReqDto) {
        try {
            OrderMenu updatedOrderMenu = cartService.updateCartMenu(orderMenuId, orderMenuReqDto);
            return ResponseEntity.ok(updatedOrderMenu);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * 장바구니 항목 삭제
     */
    @DeleteMapping("/{orderMenuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long orderMenuId) {
        try {
            cartService.deleteMenuFromCart(orderMenuId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
