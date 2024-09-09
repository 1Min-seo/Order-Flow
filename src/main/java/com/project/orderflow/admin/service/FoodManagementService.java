package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.*;
import com.project.orderflow.admin.dto.FoodRegistDto;
import com.project.orderflow.admin.repository.FoodManagementRepository;
import com.project.orderflow.admin.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FoodManagementService {
    private final FoodRepository foodRepository;
    private final CategoryService categoryService;
    private final FoodManagementRepository foodManagementRepository;
    private final OwnerService ownerService;

    public Food saveFood(Owner owner, FoodRegistDto foodRegistDto) {
        FoodManagement existingFoodManagement = foodManagementRepository.findByOwner(owner);

        if (existingFoodManagement == null) {
            existingFoodManagement = FoodManagement.builder()
                    .owner(owner)
                    .build();
            foodManagementRepository.save(existingFoodManagement);
        }

        Food food = Food.builder()
                .name(foodRegistDto.getName())
                .description(foodRegistDto.getDescription())
                .price(foodRegistDto.getPrice())
                .foodManagement(existingFoodManagement)
                .build();

        return foodRepository.save(food);
    }

}
