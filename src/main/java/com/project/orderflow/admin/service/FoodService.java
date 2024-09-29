package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Category;
import com.project.orderflow.admin.domain.Food;
//import com.project.orderflow.admin.domain.FoodManagement;
import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.FoodRegistDto;
import com.project.orderflow.admin.dto.FoodUpdateDto;
import com.project.orderflow.admin.repository.CategoryRepository;
//import com.project.orderflow.admin.repository.FoodManagementRepository;
import com.project.orderflow.admin.repository.FoodRepository;
import com.project.orderflow.admin.repository.OwnerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FoodService {
    private final FoodRepository foodRepository;
    //private final FoodManagementRepository foodManagementRepository;
    private final CategoryRepository categoryRepository;
    private final OwnerRepository ownerRepository;

    public Food saveFood(Owner owner, FoodRegistDto foodRegistDto) {
        String categoryName = foodRegistDto.getCategoryName();
        log.info("카테고리 이름: " + categoryName);

        List<Category> allCategories = categoryRepository.findAll();
        List<String> allowedCategories = allCategories.stream().map(Category::getName).toList();

        if (!Category.isValidCategoryName(categoryName, allowedCategories)) {
            throw new IllegalArgumentException("유효하지 않은 카테고리입니다: " + categoryName);
        }

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다: " + categoryName));

        Food food = Food.builder()
                .name(foodRegistDto.getName())
                .description(foodRegistDto.getDescription())
                .price(foodRegistDto.getPrice())
                .category(category)
                .build();

        log.info(foodRegistDto.getName() + " 의 카테고리: " + categoryName);
        return foodRepository.save(food);
    }


    public Food updateFood(Long ownerId, Long foodId, FoodUpdateDto foodUpdateDto) {
        Owner findOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 점주를 찾을 수 없습니다."));

        Food selectUpdateFood= foodRepository.findById(foodId)
                .orElseThrow(()-> new IllegalArgumentException("해당 음식이 존재하지 않습니다."));

        selectUpdateFood.setName(foodUpdateDto.getName());
        selectUpdateFood.setDescription(foodUpdateDto.getDescription());
        selectUpdateFood.setPrice(foodUpdateDto.getPrice());

        return foodRepository.save(selectUpdateFood);
    }

    public void deleteFood(Long ownerId, Long foodId){

        Owner findOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 점주를 찾을 수 없습니다."));
        foodRepository.deleteById(foodId);
    }
}
