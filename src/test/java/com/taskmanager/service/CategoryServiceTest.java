package com.taskmanager.service;

import com.taskmanager.model.Category;
import com.taskmanager.repository.CategoryRepository;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeMethod
    public void setUp() {
        categoryRepository = new CategoryRepository();
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    public void testAddAndGetCategory() {
        Category cat = new Category("id1", "Cat1", "Desc");
        categoryService.addCategory(cat);
        Category ret = categoryService.getCategoryById("id1");
        Assert.assertEquals(ret.getName(), "Cat1");
    }

    @Test
    public void testUpdateCategory() {
        Category cat = new Category("id2", "Start", "Start Desc");
        categoryService.addCategory(cat);
        cat.setName("Changed");
        categoryService.updateCategory(cat);
        Assert.assertEquals(categoryService.getCategoryById("id2").getName(), "Changed");
    }

    @Test
    public void testDeleteCategory() {
        Category cat = new Category("id3", "Delete", "Del");
        categoryService.addCategory(cat);
        categoryService.deleteCategory("id3");
        Assert.assertNull(categoryService.getCategoryById("id3"));
    }

    @Test
    public void testGetAllCategories() {
        categoryService.addCategory(new Category("a", "A", ""));
        categoryService.addCategory(new Category("b", "B", ""));
        List<Category> all = categoryService.getAllCategories();
        Assert.assertEquals(all.size(), 2);
    }
}
