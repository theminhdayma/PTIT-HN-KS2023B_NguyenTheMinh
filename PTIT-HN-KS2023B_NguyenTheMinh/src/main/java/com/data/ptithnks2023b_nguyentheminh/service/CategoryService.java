package com.data.ptithnks2023b_nguyentheminh.service;

import com.data.ptithnks2023b_nguyentheminh.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    boolean addCategory(Category category);
    boolean updateCategory(Category category);
    boolean deleteCategory(int id);
    List<Category> searchCategories(String categoryName);
    boolean checkNameExists(String categoryName);
}
