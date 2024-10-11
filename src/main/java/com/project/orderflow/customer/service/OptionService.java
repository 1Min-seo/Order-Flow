package com.project.orderflow.customer.service;

import com.project.orderflow.customer.domain.ItemOption; // Option을 ItemOption으로 변경
import com.project.orderflow.customer.repository.OptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    // 옵션 등록
    public ItemOption addOption(Long ownerId, String optionName) { // 반환 타입을 ItemOption으로 변경
        ItemOption itemOption = ItemOption.builder() // Option을 ItemOption으로 변경
                .optionName(optionName)
                .ownerId(ownerId)
                .build();
        return optionRepository.save(itemOption); // 저장 시에도 ItemOption으로 저장
    }

    // ownerId에 따른 옵션 조회
    public List<ItemOption> getOptionsByOwnerId(Long ownerId) { // 반환 타입을 List<ItemOption>으로 변경
        return optionRepository.findByOwnerId(ownerId); // Option을 ItemOption으로 변경
    }

    // 옵션 삭제
    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }
}
