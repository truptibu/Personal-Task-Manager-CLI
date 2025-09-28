package com.taskmanager.service;

import com.taskmanager.model.Category;
import com.taskmanager.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(Category category) {
        categoryRepository.addCategory(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.updateCategory(category);
    }

    public void deleteCategory(String categoryId) {
        categoryRepository.deleteCategory(categoryId);
    }

    public Category getCategoryById(String categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories().stream().collect(Collectors.toList());
    }
}
