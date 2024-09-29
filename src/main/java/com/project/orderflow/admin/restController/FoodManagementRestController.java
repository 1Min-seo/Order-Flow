package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.FoodRegistDto;
import com.project.orderflow.admin.dto.FoodUpdateDto;
import com.project.orderflow.admin.service.FoodService;
import com.project.orderflow.admin.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/food-management")
public class FoodManagementRestController {
    private final FoodService foodService;
    private final OwnerService ownerService;

    @PostMapping("/{ownerId}/foodRegister")
    public ResponseEntity<?> registFood(@RequestBody FoodRegistDto foodRegistDto, @PathVariable Long ownerId) {
        try {
            Owner owner = ownerService.findOwnerById(ownerId);

            if (owner == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 점주를 찾을 수 없습니다.");
            }

            foodService.saveFood(owner, foodRegistDto);

            return ResponseEntity.ok("음식 등록 성공!");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("음식 등록 중 오류가 발생했습니다.");
        }
    }


    @PostMapping("{ownerId}/update/{foodId}")
    public ResponseEntity<?> updateFood(@PathVariable Long ownerId, @PathVariable Long foodId, @RequestBody FoodUpdateDto foodUpdateDto) {
        //Owner owner= ownerService.findOwnerById(ownerId);

        foodService.updateFood(ownerId,foodId,foodUpdateDto);

        return ResponseEntity.ok("음식 수정 성공");
    }

    @DeleteMapping("/{ownerId}/delete/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable Long ownerId, @PathVariable Long foodId) {
        Owner owner= ownerService.findOwnerById(ownerId);
        foodService.deleteFood(ownerId,foodId);

        return ResponseEntity.ok("음식 삭제 성공");
    }

}
