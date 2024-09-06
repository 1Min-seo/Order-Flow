package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Food;
import com.project.orderflow.admin.dto.FoodRegistDto;
import com.project.orderflow.admin.dto.FoodUpdateDto;
import com.project.orderflow.admin.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public Food saveFood(FoodRegistDto foodRegistDto) {
        Food food=Food.builder()
                .name(foodRegistDto.getName())
                .description(foodRegistDto.getDescription())
                .price(foodRegistDto.getPrice())
                .build();

        return foodRepository.save(food);
    }

    public Food updateFood(FoodUpdateDto foodUpdateDto, Long id) {
        Food food=foodRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("찾으려는 음식이 없습니다."));

        food.setName(foodUpdateDto.getName());
        food.setDescription(foodUpdateDto.getDescription());
        food.setPrice(foodUpdateDto.getPrice());

        return foodRepository.save(food);
    }

    public void deleteFood(Long id){
        Food food=foodRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("찾으려는 음식이 없습니다."));
        foodRepository.delete(food);
    }
}
