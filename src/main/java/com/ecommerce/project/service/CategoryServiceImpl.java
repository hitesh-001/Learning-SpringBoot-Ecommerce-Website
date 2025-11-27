package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
//    private List<Category> categories = new ArrayList<>();
//    private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No categories found till now!!!");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (!Objects.isNull(savedCategory)) {
            throw new APIException("Category with the name " + category.getCategoryName() + " already exist !!!");
        }
//        category.setCategoryId(nextId++);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();
//        Category category = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
//        categoryRepository.delete(category);
//        return "Category with categoryId " + category.getCategoryId() + " has been deleted successfully !!";

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        categoryRepository.delete(category);
        return "Category with categoryId " + category.getCategoryId() + " has been deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
//        Optional<Category> optionalCategory = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst();
//        if (optionalCategory.isPresent()) {
//            Category existingCategory = optionalCategory.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            Category savedCategory = categoryRepository.save(existingCategory);
//            return savedCategory;
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
//        }
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);
        Category savedCategory = savedCategoryOptional
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        category.setCategoryId(categoryId);
        categoryRepository.save(category);
        return savedCategory;
    }


}
