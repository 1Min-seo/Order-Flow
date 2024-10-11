package com.project.orderflow.customer.service;

import com.project.orderflow.customer.domain.ItemOption; // Option을 ItemOption으로 변경
import com.project.orderflow.customer.domain.OptionOrder;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import com.project.orderflow.customer.repository.OptionOrderRepository;
import com.project.orderflow.customer.repository.OptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OptionOrderService {

    private final OptionOrderRepository optionOrderRepository;
    private final OptionRepository optionRepository;

    public OptionOrderService(OptionOrderRepository optionOrderRepository, OptionRepository optionRepository) {
        this.optionOrderRepository = optionOrderRepository;
        this.optionRepository = optionRepository;
    }

    // 옵션 주문하기
    public OptionOrder placeOptionOrder(Long ownerId, Long optionId, Integer quantity, Long tableId) {
        ItemOption itemOption = optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옵션이 존재하지 않습니다."));

        OptionOrder optionOrder = OptionOrder.builder()
                .itemOption(itemOption)
                .ownerId(ownerId)
                .quantity(quantity)
                .tableId(tableId)
                .orderStatus(OrderStatus.PENDING) // 기본 상태는 비완료
                .orderTime(LocalDateTime.now()) // 주문 시간 설정
                .build();

        return optionOrderRepository.save(optionOrder);
    }

    // 옵션 주문 상태 변경 (비완료 -> 완료)
    public OptionOrder completeOptionOrder(Long optionOrderId) {
        OptionOrder optionOrder = optionOrderRepository.findById(optionOrderId)
                .orElseThrow(() -> new IllegalArgumentException("옵션 주문이 존재하지 않습니다."));

        optionOrder.setOrderStatus(OrderStatus.COMPLETED); // 상태 완료로 변경
        return optionOrderRepository.save(optionOrder);
    }
    // 옵션 주문 내역 조회
    public List<OptionOrder> getOptionOrderHistory(Long ownerId) {
        return optionOrderRepository.findByOwnerId(ownerId);
    }
}

