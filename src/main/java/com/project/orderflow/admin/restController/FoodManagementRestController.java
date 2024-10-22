package com.project.orderflow.admin.restController;

import com.project.orderflow.admin.domain.Category;
import com.project.orderflow.admin.domain.CategoryDto;
import com.project.orderflow.admin.domain.Food;
import com.project.orderflow.admin.domain.Owner;
import com.project.orderflow.admin.dto.FoodDto;
import com.project.orderflow.admin.dto.FoodRegistDto;
import com.project.orderflow.admin.dto.FoodUpdateDto;
import com.project.orderflow.admin.repository.FoodRepository;
import com.project.orderflow.admin.service.FoodService;
import com.project.orderflow.admin.service.OwnerService;
//import io.swagger.annotations.ApiParam;
//import io.swagger.v3.oas.annotations.Parameter;
import com.project.orderflow.admin.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "음식관리", description = "음식관리 API")
@RequestMapping("/api/food-management")
public class FoodManagementRestController {
    private final FoodService foodService;
    private final OwnerService ownerService;
    private final S3Service s3Service;
    private final FoodRepository foodRepository;

    @Operation(
            summary = "음식등록[관리자]",
            description = "{ownerId} JWT에서 추출한 id 값, name 음식 이름, image는 이미지, description 설명, price 가격, categoryName: 카테고리 이름"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "음식 등록 성공"),
            @ApiResponse(responseCode = "404", description = "해당 점주를 찾을 수 없음", content = @Content),
            @ApiResponse(responseCode = "500", description = "음식 등록 중 오류 발생", content = @Content)
    })
    @PostMapping(value = "/{ownerId}/foodRegister", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registFood(@ModelAttribute FoodRegistDto foodRegistDto, @PathVariable Long ownerId) {
        try {
            Owner owner = ownerService.findOwnerById(ownerId);

            if (owner == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 점주를 찾을 수 없습니다.");
            }

            foodService.saveFood(ownerId, foodRegistDto);
            return ResponseEntity.ok("음식 등록 성공!");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("음식 등록 중 오류가 발생했습니다.");
        }
    }

    @Operation(
            summary = "음식수정[관리자]",
            description = "{ownerId} JWT에서 추출한 id 값, foodId 해당 음식의 id 값, name 음식 이름, image는 이미지, description 설명, price 가격, categoryName: 카테고리 이름"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "음식 수정 성공"),
            @ApiResponse(responseCode = "404", description = "해당 점주 또는 음식을 찾을 수 없음", content = @Content),
            @ApiResponse(responseCode = "500", description = "음식 수정 중 오류 발생", content = @Content)
    })
    @PostMapping(value = "{ownerId}/update/{foodId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFood(@PathVariable Long ownerId, @PathVariable Long foodId, @ModelAttribute FoodUpdateDto foodUpdateDto) {
        try {
            Owner owner = ownerService.findOwnerById(ownerId);
            if (owner == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 점주를 찾을 수 없습니다.");
            }

            Optional<Food> foodOptional = foodRepository.findById(foodId);
            if (foodOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 음식을 찾을 수 없습니다.");
            }

            foodService.updateFood(owner.getId(), foodId, foodUpdateDto);
            return ResponseEntity.ok("음식 수정 성공");

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("음식 수정 중 오류가 발생했습니다.");
        }
    }

    @Operation(
            summary = "음식삭제[관리자]",
            description = "{ownerId} JWT에서 추출한 id 값, foodId 해당 음식의 id 값"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "음식 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 점주 또는 음식을 찾을 수 없음", content = @Content)
    })
    @DeleteMapping("/{ownerId}/delete/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable Long ownerId, @PathVariable Long foodId) {
        Owner owner = ownerService.findOwnerById(ownerId);
        foodService.deleteFood(ownerId, foodId);
        return ResponseEntity.ok("음식 삭제 성공");
    }

    @Operation(
            summary = "음식조회[고객/관리자]",
            description = "{ownerId} JWT에서 추출한 id 값"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "음식 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodDto.class))),
            @ApiResponse(responseCode = "204", description = "음식 없음", content = @Content)
    })
    @GetMapping("/{ownerId}/foods")
    public ResponseEntity<?> getFoodsByOwnerId(@PathVariable(name = "ownerId") Long ownerId) {
        List<FoodDto> foods = foodService.getFoodsByOwnerId(ownerId);

        if (foods.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foods);
    }

    @Operation(
            summary = "카테고리 조회[고객,관리자]",
            description = "{ownerId} JWT에서 추출한 id 값"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카테고리 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "array", example = "[\"BEST\", \"DESSERT\"]"))),
            @ApiResponse(responseCode = "204", description = "카테고리 없음", content = @Content)
    })
    @GetMapping("/{ownerId}/categories")
    public ResponseEntity<?> getCategoriesByOwnerId(@PathVariable(name = "ownerId") Long ownerId) {
        List<String> categories = foodService.getCategoriesByOwnerId(ownerId);

        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }


    @Operation(
            summary = "카테고리 등록[관리자]",
            description = "새로운 카테고리를 등록합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "카테고리 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content),
            @ApiResponse(responseCode = "500", description = "카테고리 등록 중 오류 발생", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerCategory(@RequestBody CategoryDto categoryDto) {
        try {
            Category category = foodService.saveCategory(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("카테고리 등록 성공: " + category.getName());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("카테고리 등록 오류: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("카테고리 등록 중 오류가 발생했습니다.");
        }
    }
}