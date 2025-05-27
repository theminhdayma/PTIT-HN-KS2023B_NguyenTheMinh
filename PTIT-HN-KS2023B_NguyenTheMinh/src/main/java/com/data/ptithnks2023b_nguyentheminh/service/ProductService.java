package com.data.ptithnks2023b_nguyentheminh.service;

import com.data.ptithnks2023b_nguyentheminh.model.Product;

import java.util.List;

public interface ProductService {
    boolean addProduct(Product product);
    Product getProductById(int id);
    boolean updateProduct(Product product);
    boolean deleteProduct(int id);
    List<Product> getAllProducts();
    List<Product> searchProducts(String productName);
    boolean checkNameExists(String productName);
}
