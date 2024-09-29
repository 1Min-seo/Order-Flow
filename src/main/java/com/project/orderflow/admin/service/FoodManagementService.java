//package com.project.orderflow.admin.service;
//
//import com.project.orderflow.admin.domain.*;
//import com.project.orderflow.admin.dto.FoodRegistDto;
//import com.project.orderflow.admin.repository.FoodManagementRepository;
//import com.project.orderflow.admin.repository.FoodRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Service
//@RequiredArgsConstructor
//public class FoodManagementService {
//    private final FoodManagementRepository foodManagementRepository;
//
//    public List<Food> getFoodsCategory(Category category){
//        List<FoodManagement> foodManagement = foodManagementRepository.findByCategory(category);
//        return foodManagement.stream()
//                .flatMap(fm->fm.getFoods().stream())
//                .collect(Collectors.toList());
//    }
//
//}
