package com.project.orderflow.admin.service;

import com.project.orderflow.admin.domain.Category;
import com.project.orderflow.admin.domain.CategoryType;
import com.project.orderflow.admin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category save(CategoryType categoryType){
        Category category = new Category();
        category.setType(categoryType);

        return categoryRepository.save(category);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category findByCategoryType(CategoryType categoryType){
        return categoryRepository.findByType(categoryType);
    }
}
