package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Category;
import com.project.orderflow.admin.domain.CategoryType;
import com.project.orderflow.admin.domain.FoodManagement;
import com.project.orderflow.admin.domain.Owner;
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

    public FoodManagement saveFoodManagement(Owner owner, CategoryType categoryType) {
        FoodManagement foodManagement=FoodManagement.builder().build();
        Category getCategory= categoryService.findByCategoryType(categoryType);
        //FoodManagement foodManagement=new FoodManagement();
        foodManagement.setCategory(getCategory);


        return foodManagementRepository.save(foodManagement);
    }





}
