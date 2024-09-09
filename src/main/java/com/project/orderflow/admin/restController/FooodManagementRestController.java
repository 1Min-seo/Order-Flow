package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.CategoryType;
import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.FoodRegistDto;
import com.project.orderflow.admin.service.FoodManagementService;
import com.project.orderflow.admin.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food-management")
public class FooodManagementRestController {
    private final FoodManagementService foodManagementService;
    private final OwnerService ownerService;

    @PostMapping("register/{ownerId}")
    public ResponseEntity<?> registFood(@RequestBody  FoodRegistDto foodRegistDto, @PathVariable Long ownerId) {
        Owner owner= ownerService.findOwnerById(ownerId);

        foodManagementService.saveFood(owner,foodRegistDto);

        return ResponseEntity.ok("음식 등록 성공!");
    }
}
