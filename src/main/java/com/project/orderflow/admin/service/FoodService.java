package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Category;
import com.project.orderflow.admin.domain.CategoryDto;
import com.project.orderflow.admin.domain.Food;
//import com.project.orderflow.admin.domain.FoodManagement;
import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.FoodDto;
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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FoodService {
    private final FoodRepository foodRepository;
    //private final FoodManagementRepository foodManagementRepository;
    private final CategoryRepository categoryRepository;
    private final OwnerRepository ownerRepository;
    private final S3Service s3Service;

    public Food saveFood(long ownerId, FoodRegistDto foodRegistDto) throws IOException {
        String categoryName = foodRegistDto.getCategoryName();

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다: " + categoryName));

        String fileName = foodRegistDto.getImage().getOriginalFilename();
        s3Service.uploadFile(foodRegistDto.getImage().getInputStream(), fileName);

        String imageUrl = "https://orderflow-bk.s3.amazonaws.com/uploads/" + fileName;

        System.out.println(ownerId);

        Food food = Food.builder()
                .name(foodRegistDto.getName())
                .imageUrl(imageUrl)  // S3 이미지 URL 설정
                .tableManagementId(ownerId)
                .description(foodRegistDto.getDescription())
                .price(foodRegistDto.getPrice())
                .category(category)
                .build();

        return foodRepository.save(food);
    }

    public void updateFood(Long ownerId, Long foodId, FoodUpdateDto foodUpdateDto) throws IOException {
//        Owner findOwner = ownerRepository.findById(ownerId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 점주를 찾을 수 없습니다."));

        Food selectUpdateFood= foodRepository.findById(foodId)
                .orElseThrow(()-> new IllegalArgumentException("해당 음식이 존재하지 않습니다."));

        selectUpdateFood.setName(foodUpdateDto.getName());
        selectUpdateFood.setDescription(foodUpdateDto.getDescription());
        selectUpdateFood.setPrice(foodUpdateDto.getPrice());
        //selectUpdateFood.setCategory(foodUpdateDto.getCategoryName());
        String deleteImageFile=selectUpdateFood.getImageUrl();
        s3Service.deleteFile(deleteImageFile);

        String fileName = foodUpdateDto.getImage().getOriginalFilename();
        s3Service.uploadFile(foodUpdateDto.getImage().getInputStream(), fileName);

        String imageUrl = "https://orderflow-bk.s3.amazonaws.com/" + fileName;
        selectUpdateFood.setImageUrl(imageUrl);

    }

    public void deleteFood(Long ownerId, Long foodId){

        Owner findOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 점주를 찾을 수 없습니다."));
        foodRepository.deleteById(foodId);
    }

    public void updateFood(Food existingFood) {
    }

    public List<FoodDto> getFoodsByOwnerId(Long ownerId) {
        return foodRepository.findByTableManagementId(ownerId).stream()
                .map(food -> new FoodDto(food.getName(), food.getCategory().getName(), food.getPrice(), food.getDescription(), food.getImageUrl()))
                .collect(Collectors.toList());
    }

    public List<String> getCategoriesByOwnerId(Long ownerId) {
        return foodRepository.findByTableManagementId(ownerId).stream()
                .map(food -> food.getCategory().getName())
                .distinct()
                .collect(Collectors.toList());
    }

    public Category saveCategory(CategoryDto categoryDto) {
        if (categoryRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 카테고리입니다: " + categoryDto.getName());
        }

        Category category = new Category(categoryDto.getName());
        return categoryRepository.save(category);
    }
}
