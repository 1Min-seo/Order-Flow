package com.project.orderflow.admin.config;

import com.project.orderflow.admin.domain.Category;
import com.project.orderflow.admin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        List<String> initialCategories = List.of("BEST", "SIGNATURE", "MAIN", "SIDE", "DRINK");

        for (String categoryName : initialCategories) {
            if (categoryRepository.findByName(categoryName).isEmpty()) {
                Category category = new Category(categoryName);
                categoryRepository.save(category);
            }
        }
    }
}

