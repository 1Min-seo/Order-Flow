package com.project.orderflow.customer.service;

import com.project.orderflow.customer.domain.OptionMenu;
import com.project.orderflow.customer.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionService {

    private final OptionRepository optionRepository;
    
    public List<OptionMenu> getAllOptions(){
        return optionRepository.findAll();
    }
/*
    public Option validateOption(Long optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("옵션이 존재하지 않습니다."));
    }

 */
}