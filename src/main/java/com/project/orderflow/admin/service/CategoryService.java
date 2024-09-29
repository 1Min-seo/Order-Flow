//package com.project.orderflow.admin.service;
//
//import com.project.orderflow.admin.domain.Category;
//import com.project.orderflow.admin.domain.CategoryType;
//import com.project.orderflow.admin.repository.CategoryRepository;
//import jakarta.annotation.PostConstruct;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class CategoryService {
//    private final CategoryRepository categoryRepository;
//
//    @PostConstruct
//    public void init() {
//        initializeCategories();
//    }
//
//    public void initializeCategories() {
//        for (CategoryType categoryType : CategoryType.values()) {
//            Category category = new Category();
//            category.setCategoryType(categoryType);
//            save(categoryType);
//        }
//    }
//
//    public boolean categoryExists(CategoryType categoryType) {
//        return categoryRepository.findByCategoryType(categoryType) != null;
//    }
//
//    public Category save(CategoryType categoryType) {
//        if (categoryExists(categoryType)) {
//            return categoryRepository.findByCategoryType(categoryType);
//        } else {
//            Category category = new Category();
//            category.setCategoryType(categoryType);
//            return categoryRepository.save(category);
//        }
//    }
//
//    public List<Category> findAll() {
//        return categoryRepository.findAll();
//    }
//
//    public Category findByCategoryType(CategoryType categoryType) {
//        return categoryRepository.findByCategoryType(categoryType);
//    }
//}
