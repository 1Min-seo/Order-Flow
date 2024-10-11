package com.project.orderflow.customer.service;

import com.project.orderflow.customer.domain.ItemOption; // Option을 ItemOption으로 변경
import com.project.orderflow.customer.domain.OptionOrder;
import com.project.orderflow.customer.repository.OptionOrderRepository;
import com.project.orderflow.customer.repository.OptionRepository;
import org.springframework.stereotype.Service;

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
    public OptionOrder placeOptionOrder(Long ownerId, Long optionId, Integer quantity, Long tableId) { // tableId 추가
        ItemOption itemOption = optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 옵션이 존재하지 않습니다."));

        OptionOrder optionOrder = OptionOrder.builder()
                .itemOption(itemOption)
                .ownerId(ownerId)
                .quantity(quantity)
                .tableId(tableId) // 테이블 ID 저장
                .build();

        return optionOrderRepository.save(optionOrder);
    }

    // 옵션 주문 내역 조회
    public List<OptionOrder> getOptionOrderHistory(Long ownerId) {
        return optionOrderRepository.findByOwnerId(ownerId);
    }
}

