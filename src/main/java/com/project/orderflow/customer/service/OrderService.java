package com.project.orderflow.customer.service;

import com.project.orderflow.customer.domain.Menu;
import com.project.orderflow.customer.domain.OrderMenu;
import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.customer.domain.TableOrder;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import com.project.orderflow.customer.dto.OrderMenuReqDto;
import com.project.orderflow.customer.dto.OrderReqDto;
import com.project.orderflow.customer.repository.MenuRepository;
import com.project.orderflow.customer.repository.OrderRepository;
import com.project.orderflow.customer.repository.SeatRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public OrderReqDto getOrderById(Long orderId) {
        Optional<TableOrder> order = orderRepository.findById(orderId); // 주문 조회
        if (order.isPresent()) {
            TableOrder tableOrder = order.get();
            return new OrderReqDto(
                    tableOrder.getTable().getId(),
                    tableOrder.getOrderAt(),
                    tableOrder.getOrderMenus().stream()
                            .map(menu -> new OrderMenuReqDto(
                                    menu.getMenu().getId(),
                                    menu.getMenu().getName(),
                                    menu.getQuantity()))
                            .collect(Collectors.toList())
            );
        } else {
            return null; // 주문이 없을 경우 일단 null 반환
        }
    }

    @Transactional
    public TableOrder order(OrderReqDto orderReqDto) {
        Seat table = seatRepository.findById(orderReqDto.getTableId()).orElseThrow(
                () -> new RuntimeException("Table not found")
        );

        // 기존의 TableOrder 조회
        TableOrder tableOrder = orderRepository.findByTableAndStatus(table, OrderStatus.COOK)
                .orElse(TableOrder.builder()
                        .table(table)
                        .orderAt(orderReqDto.getOrderAt())
                        .status(OrderStatus.COOK)
                        .build());

        // OrderMenu 리스트 생성
        List<OrderMenu> orderMenuList = orderReqDto.getOrderMenus().stream()
                .map(orderMenuReqDto -> {
                    Menu menu = menuRepository.findById(orderMenuReqDto.getMenuId())
                            .orElseThrow(() -> new RuntimeException("Menu not found"));
                    return OrderMenu.builder()
                            .menu(menu)
                            .quantity(orderMenuReqDto.getQuantity())
                            .orderPrice(menu.getPrice() * orderMenuReqDto.getQuantity()) // 총 가격 계산
                            .count(orderMenuReqDto.getQuantity())
                            .build();
                }).collect(Collectors.toList());

        // 기존 TableOrder에 새로운 OrderMenu 추가
        tableOrder.addOrderMenus(orderMenuList);

        return orderRepository.save(tableOrder);
    }
}
