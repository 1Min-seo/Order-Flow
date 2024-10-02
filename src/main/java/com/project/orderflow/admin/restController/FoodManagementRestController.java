package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Category;
import com.project.orderflow.admin.domain.Food;
import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.FoodRegistDto;
import com.project.orderflow.admin.dto.FoodUpdateDto;
import com.project.orderflow.admin.repository.FoodRepository;
import com.project.orderflow.admin.service.FoodService;
import com.project.orderflow.admin.service.OwnerService;
import com.project.orderflow.admin.service.S3Service;
//import io.swagger.annotations.ApiParam;
//import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/food-management")
public class FoodManagementRestController {
    private final FoodService foodService;
    private final OwnerService ownerService;
    private final S3Service s3Service;
    private final FoodRepository foodRepository;


    @PostMapping(value = "/{ownerId}/foodRegister", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registFood(@ModelAttribute FoodRegistDto foodRegistDto, @PathVariable Long ownerId) {
        try {
            Owner owner = ownerService.findOwnerById(ownerId);

            if (owner == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 점주를 찾을 수 없습니다.");
            }

            // S3에 이미지 업로드
            String imageUrl = s3Service.uploadFile(foodRegistDto.getImage().getInputStream(), foodRegistDto.getImage().getOriginalFilename());

            foodService.saveFood(owner, foodRegistDto);

            return ResponseEntity.ok("음식 등록 성공!");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("음식 등록 중 오류가 발생했습니다.");
        }
    }

//    @PostMapping(value = "{ownerId}/update/{foodId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> updateFood(@PathVariable Long ownerId, @PathVariable Long foodId, @ModelAttribute FoodUpdateDto foodUpdateDto) {
//        try {
//
//            Owner owner = ownerService.findOwnerById(ownerId);
//            if (owner == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 점주를 찾을 수 없습니다.");
//            }

//            Optional<Food> foodOptional = foodRepository.findById(foodId);
//            if (foodOptional.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 음식을 찾을 수 없습니다.");
//            }
//            Food existingFood = foodOptional.get();
//
//            // 기존 이미지가 있을 경우 S3에서 삭제
//            if (existingFood.getImageUrl() != null) {
//                s3Service.deleteFile(existingFood.getImageUrl());
//            }
//
//
//            MultipartFile newImage = foodUpdateDto.getImage();
//            String newImageUrl = null;
//            if (newImage != null && !newImage.isEmpty()) {
//                newImageUrl = s3Service.uploadFile(newImage.getInputStream(), newImage.getOriginalFilename());
//            }
//
//            existingFood.setName(foodUpdateDto.getName());
//            existingFood.setDescription(foodUpdateDto.getDescription());
//            existingFood.setPrice(foodUpdateDto.getPrice());
//            existingFood.setCategoryName(foodUpdateDto.getCategoryName());
//            if (newImageUrl != null) {
//                existingFood.setImageUrl(newImageUrl); // 새 이미지 URL
//            }
//
//
//            foodService.updateFood(existingFood);
//
//            return ResponseEntity.ok("음식 수정 성공");
//
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("음식 수정 중 오류가 발생했습니다.");
//        }
//    }


    @DeleteMapping("/{ownerId}/delete/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable Long ownerId, @PathVariable Long foodId) {
        Owner owner = ownerService.findOwnerById(ownerId);
        foodService.deleteFood(ownerId, foodId);

        return ResponseEntity.ok("음식 삭제 성공");
    }

}
