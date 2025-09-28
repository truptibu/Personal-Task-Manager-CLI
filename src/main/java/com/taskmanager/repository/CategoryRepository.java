package com.taskmanager.repository;

import com.taskmanager.model.Category;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class CategoryRepository {
    private final Map<String, Category> categoryMap = new HashMap<>();

    public void addCategory(Category category) {
        categoryMap.put(category.getCategoryId(), category);
    }

    public Category getCategoryById(String categoryId) {
        return categoryMap.get(categoryId);
    }

    public void updateCategory(Category category) {
        categoryMap.put(category.getCategoryId(), category);
    }

    public void deleteCategory(String categoryId) {
        categoryMap.remove(categoryId);
    }

    public Collection<Category> getAllCategories() {
        return categoryMap.values();
    }

    public boolean exists(String categoryId) {
        return categoryMap.containsKey(categoryId);
    }

    public void clear() {
        categoryMap.clear();
    }
}
