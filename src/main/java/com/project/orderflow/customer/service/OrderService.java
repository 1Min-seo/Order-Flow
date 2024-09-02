package com.project.orderflow.customer.service;

import com.project.orderflow.customer.domain.OrderMenu;
import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.customer.domain.TableOrder;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import com.project.orderflow.customer.dto.OrderMenuReqDto;
import com.project.orderflow.customer.dto.TableOrderReqDto;
import com.project.orderflow.customer.repository.OrderMenuRepository;
import com.project.orderflow.customer.repository.TableOrderRepository;
import com.project.orderflow.admin.repository.SeatRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final TableOrderRepository tableOrderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final SeatRepository seatRepository;

    /**
     * 주문 생성 메서드
     */
    @Transactional
    public TableOrder order(String tableNum) {
        Seat seat = seatRepository.findByTableNum(tableNum)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        TableOrder tableOrder = tableOrderRepository.findByTableAndStatus(seat, OrderStatus.CART)
                .orElseThrow(() -> new RuntimeException("No cart found for this table"));

        int totalPrice = 0;

        // 장바구니에 있는 모든 메뉴의 상태를 ORDERED로 변경
        for (OrderMenu orderMenu : tableOrder.getOrderMenus()) {
            if (orderMenu.getStatus() == OrderStatus.CART) {
                orderMenu.changeToOrder();
                // 메뉴 가격을 총액에 더하기
                totalPrice += orderMenu.getOrderPrice();
                // 상태가 변경된 orderMenu를 저장
                orderMenuRepository.save(orderMenu);
            }
        }
        // 계산된 총액을 tableOrder에 설정
        tableOrder.updateTotalPrice(totalPrice);

        tableOrder.markAsOrdered();
        return tableOrderRepository.save(tableOrder);
    }
    /**
     * 테이블 별 주문 내역 조회
     */
    public List<TableOrderReqDto> getOrdersByTable(String tableNum) {
        Seat seat = seatRepository.findByTableNum(tableNum)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        List<TableOrder> tableOrders = tableOrderRepository.findByTable(seat);

        // 엔티티를 DTO로 변환
        return tableOrders.stream()
                .map(this::convertToDto)
                .toList();
    }
    private TableOrderReqDto convertToDto(TableOrder order) {
        List<OrderMenuReqDto> orderMenuDtos = order.getOrderMenus().stream()
                .map(menu -> new OrderMenuReqDto(menu.getId(), menu.getMenu().getName(), menu.getQuantity()))
                .toList();

        return new TableOrderReqDto(
                order.getOrderAt(),
                order.getStatus(),
                order.getTotalPrice(),
                orderMenuDtos
        );
    }
}
