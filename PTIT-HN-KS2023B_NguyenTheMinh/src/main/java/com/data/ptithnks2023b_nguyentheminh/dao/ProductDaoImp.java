package com.data.ptithnks2023b_nguyentheminh.dao;

import com.data.ptithnks2023b_nguyentheminh.model.Product;
import com.data.ptithnks2023b_nguyentheminh.model.Status;
import com.data.ptithnks2023b_nguyentheminh.utils.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImp implements ProductDao {
    @Override
    public boolean addProduct(Product product) {
        Connection conn = null;
        CallableStatement callst = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call add_product(?, ?, ?, ?, ?)}");
            callst.setString(1, product.getName());
            callst.setString(2, product.getDescription());
            callst.setDouble(3, product.getPrice());
            callst.setString(4, product.getImageUrl());
            callst.setInt(5, product.getCategoryId());
            return callst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
    }

    @Override
    public Product getProductById(int id) {
        Connection conn = null;
        CallableStatement callst = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call get_product_by_id(?)}");
            callst.setInt(1, id);
            rs = callst.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setStatus(rs.getString("status").equalsIgnoreCase("active") ? Status.ACTIVE : Status.INACTIVE);
                return product;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
        return null;
    }

    @Override
    public boolean updateProduct(Product product) {
        Connection conn = null;
        CallableStatement callst = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call update_product(?, ?, ?, ?, ?, ?)}");
            callst.setInt(1, product.getId());
            callst.setString(2, product.getName());
            callst.setString(3, product.getDescription());
            callst.setDouble(4, product.getPrice());
            callst.setString(5, product.getImageUrl());
            callst.setInt(6, product.getCategoryId());
            return callst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
    }

    @Override
    public boolean deleteProduct(int id) {
        Connection conn = null;
        CallableStatement callst = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call delete_product(?)}");
            callst.setInt(1, id);
            return callst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        Connection conn = null;
        CallableStatement callst = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call get_all_products()}");
            rs = callst.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setStatus(rs.getString("status").equalsIgnoreCase("active") ? Status.ACTIVE : Status.INACTIVE);
                product.setCreatedAt(LocalDate.parse(rs.getString("created_at").substring(0, 10)));
                products.add(product);
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
        return null;
    }

    @Override
    public List<Product> searchProducts(String productName) {
        Connection conn = null;
        CallableStatement callst = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call search_product_by_name(?)}");
            callst.setString(1, productName);
            rs = callst.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("product_id"));
                product.setName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setStatus(rs.getString("status").equalsIgnoreCase("active") ? Status.ACTIVE : Status.INACTIVE);
                product.setCreatedAt(LocalDate.parse(rs.getString("created_at").substring(0, 10)));
                products.add(product);
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
        return null;
    }

    @Override
    public boolean checkNameExists(String productName) {
        Connection conn = null;
        CallableStatement callst = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call check_name_product_exists(?)}");
            callst.setString(1, productName);
            rs = callst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
    }
}
