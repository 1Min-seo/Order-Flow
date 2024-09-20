package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Category;
import com.project.orderflow.admin.domain.Food;
import com.project.orderflow.admin.domain.FoodManagement;
import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.FoodRegistDto;
import com.project.orderflow.admin.dto.FoodUpdateDto;
import com.project.orderflow.admin.repository.FoodManagementRepository;
import com.project.orderflow.admin.repository.FoodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FoodService {
    private final FoodRepository foodRepository;
    private final FoodManagementRepository foodManagementRepository;
    private final CategoryService categoryService;

    public Food saveFood(Owner owner, FoodRegistDto foodRegistDto) {
        FoodManagement existingFoodManagement = foodManagementRepository.findByOwner(owner);

        if (existingFoodManagement == null) {
            existingFoodManagement = FoodManagement.builder()
                    .owner(owner)
                    .build();
            foodManagementRepository.save(existingFoodManagement);
        }

        log.info("푸드매니지먼트 연결");
        Category category = categoryService.findByCategoryType(foodRegistDto.getCategoryType());

        log.info(foodRegistDto.getName()+"의 카테고리: "+foodRegistDto.getCategoryType());
        if(category==null){
            log.error("해당 카테고리를 찾을 수 없습니다: " + foodRegistDto.getCategoryType());
            throw new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다.");
        }
        existingFoodManagement.setCategory(category);
        foodManagementRepository.save(existingFoodManagement);

        Food food = Food.builder()
                .name(foodRegistDto.getName())
                .description(foodRegistDto.getDescription())
                .price(foodRegistDto.getPrice())
                .foodManagement(existingFoodManagement)
                .category(category)
                .build();

        return foodRepository.save(food);
    }

    public Food updateFood(Owner owner, Long foodId, FoodUpdateDto foodUpdateDto) {
        FoodManagement existingFoodManagement = foodManagementRepository.findByOwner(owner);

        if (existingFoodManagement == null) {
            existingFoodManagement = FoodManagement.builder()
                    .owner(owner)
                    .build();
            foodManagementRepository.save(existingFoodManagement);
        }

        Food selectUpdateFood= foodRepository.findById(foodId).orElseThrow(()-> new IllegalArgumentException("해당 음식이 존재하지 않습니다."));

        selectUpdateFood.setName(foodUpdateDto.getName());
        selectUpdateFood.setDescription(foodUpdateDto.getDescription());
        selectUpdateFood.setPrice(foodUpdateDto.getPrice());

        return foodRepository.save(selectUpdateFood);
    }

    public void deleteFood(Owner owner, Long foodId){
        FoodManagement existingFoodManagement= foodManagementRepository.findByOwner(owner);
        if (existingFoodManagement == null) {
            existingFoodManagement=FoodManagement.builder()
                    .owner(owner)
                    .build();
            foodManagementRepository.save(existingFoodManagement);
        }

        foodRepository.deleteById(foodId);
    }
}
