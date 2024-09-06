package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.OptionMenu;
import com.project.orderflow.customer.domain.TableOrder;
import com.project.orderflow.customer.dto.OptionOrderReqDto;
import com.project.orderflow.customer.dto.OrderResDto;
import com.project.orderflow.customer.dto.TableOrderReqDto;
import com.project.orderflow.customer.service.OptionService;
import com.project.orderflow.customer.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
public class OrderController {
    private final OrderService orderService;
    private final OptionService optionService;

    @PostMapping
    @Operation(summary = "메뉴 주문", description = "테이블 번호에 해당하는 장바구니를 주문으로 전환하였습니다.")
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
    @Operation(summary = "테이블 주문 조회", description = "테이블 번호로 주문을 조회합니다.")
    public List<TableOrderReqDto> getOrdersByTable(@PathVariable String tableNumber) {
        return orderService.getOrdersByTable(tableNumber);
    }

    @GetMapping("/option/list")
    @Operation(summary = "옵션 목록 조회", description = "주문하고자하는 옵션 목록을 조회합니다.")
    public ResponseEntity<List<OptionMenu>> getOptions() {
        List<OptionMenu> options = optionService.getAllOptions();
        return ResponseEntity.ok(options);
    }

    @PostMapping("/option")
    @Operation(summary = "옵션 주문", description = "해당하는 옵션을 주문합니다.")
    public ResponseEntity<Void> orderOptions(@RequestBody OptionOrderReqDto optionOrderReqDto) {
        // 테이블 번호 확인
        String tableNum = optionOrderReqDto.getTableNumber();
        log.info("주문한 테이블 번호: {}", tableNum);

        // 옵션 주문 처리
        optionOrderReqDto.getOptionOrders().forEach(optionOrder -> {
            log.info("Option ID = {}, Quantity = {}, Table Number = {}",
                    optionOrder.getOptionId(),
                    optionOrder.getQuantity(),
                    tableNum);
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }
}