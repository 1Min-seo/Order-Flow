package com.project.orderflow.admin.repository;

import com.project.orderflow.admin.domain.Category;
import com.project.orderflow.admin.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryType(CategoryType categorytype);
}
