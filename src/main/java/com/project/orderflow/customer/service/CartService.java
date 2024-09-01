package com.project.orderflow.customer.service;

import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.customer.domain.Menu;
import com.project.orderflow.customer.domain.OrderMenu;
import com.project.orderflow.customer.domain.TableOrder;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import com.project.orderflow.customer.dto.OrderMenuReqDto;
import com.project.orderflow.customer.repository.MenuRepository;
import com.project.orderflow.customer.repository.OrderMenuRepository;
import com.project.orderflow.admin.repository.SeatRepository;
import com.project.orderflow.customer.repository.TableOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final OrderMenuRepository orderMenuRepository;
    private final TableOrderRepository tableOrderRepository;
    private final MenuRepository menuRepository;
    private final SeatRepository seatRepository;

    /**
     * 장바구니 메뉴 추가 - 여러 항목이든 한 항목이든
     */
    @Transactional
    public void addMenusToCart(String tableNum, List<OrderMenuReqDto> orderMenuReqDtoList) {
        // 테이블 번호로 Seat 객체 조회
        Seat seat = seatRepository.findByTableNum(tableNum)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        // 테이블 번호에 해당하는 장바구니 주문 조회
        TableOrder tableOrder = tableOrderRepository.findByTableAndStatus(seat, OrderStatus.CART)
                .orElseGet(() -> {
                    // 존재하지 않으면 새로 생성
                    TableOrder newOrder = TableOrder.builder()
                            .table(seat)
                            .orderAt(LocalDateTime.now())
                            .status(OrderStatus.CART)
                            .build();
                    tableOrderRepository.save(newOrder);
                    return newOrder;
                });
        // OrderMenu 객체 리스트 생성
        List<OrderMenu> orderMenus = new ArrayList<>();
        for (OrderMenuReqDto orderMenuReqDto : orderMenuReqDtoList) {
            // OrderMenu 객체 생성
            Menu menu = menuRepository.findById(orderMenuReqDto.getMenuId())
                    .orElseThrow(() -> new RuntimeException("Menu not found"));

            OrderMenu orderMenu = OrderMenu.builder()
                    .menu(menu)
                    .quantity(orderMenuReqDto.getQuantity())
                    .orderPrice(menu.getPrice() * orderMenuReqDto.getQuantity())
                    .status(OrderStatus.CART)
                    .tableOrder(tableOrder)
                    .build();

            orderMenus.add(orderMenu);
        }
        // OrderMenu 일괄 저장
        orderMenuRepository.saveAll(orderMenus);

        // TableOrder에 OrderMenu 추가
        tableOrder.addOrderMenus(orderMenus);
        tableOrderRepository.save(tableOrder);
    }
}
