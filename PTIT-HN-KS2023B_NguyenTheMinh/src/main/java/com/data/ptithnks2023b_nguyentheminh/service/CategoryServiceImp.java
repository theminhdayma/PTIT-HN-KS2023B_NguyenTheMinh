package com.data.ptithnks2023b_nguyentheminh.service;

import com.data.ptithnks2023b_nguyentheminh.dao.CategoryDao;
import com.data.ptithnks2023b_nguyentheminh.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }

    @Override
    public boolean addCategory(Category category) {
        return categoryDao.addCategory(category);
    }

    @Override
    public boolean updateCategory(Category category) {
        return categoryDao.updateCategory(category);
    }

    @Override
    public boolean deleteCategory(int id) {
        try {
            return categoryDao.deleteCategory(id);
        } catch (RuntimeException e) {
            throw e;
        }
    }
    @Override
    public List<Category> searchCategories(String categoryName) {
        return categoryDao.searchCategories(categoryName);
    }

    @Override
    public boolean checkNameExists(String categoryName) {
        return categoryDao.checkNameExists(categoryName);
    }
}
