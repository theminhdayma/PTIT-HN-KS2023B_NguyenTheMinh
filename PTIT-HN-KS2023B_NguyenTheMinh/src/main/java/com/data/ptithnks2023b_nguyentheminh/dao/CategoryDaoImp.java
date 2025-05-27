package com.data.ptithnks2023b_nguyentheminh.dao;

import com.data.ptithnks2023b_nguyentheminh.model.Category;
import com.data.ptithnks2023b_nguyentheminh.model.Status;
import com.data.ptithnks2023b_nguyentheminh.utils.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDaoImp implements CategoryDao {
    @Override
    public List<Category> getAllCategories() {
        Connection conn = null;
        CallableStatement callst = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call get_all_categories()}");
            rs = callst.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                category.setStatus(rs.getString("status").equalsIgnoreCase("active") ? Status.ACTIVE : Status.INACTIVE);
                categories.add(category);
            }
            return categories;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
        return null;
    }

    @Override
    public Category getCategoryById(int id) {
        Connection conn = null;
        CallableStatement callst = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call get_category_by_id(?)}");
            callst.setInt(1, id);
            rs = callst.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                category.setStatus(rs.getString("status").equalsIgnoreCase("active") ? Status.ACTIVE : Status.INACTIVE);
                return category;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
        return null;
    }

    @Override
    public boolean addCategory(Category category) {
        Connection conn = null;
        CallableStatement callst = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call add_category(?, ?)}");
            callst.setString(1, category.getName());
            callst.setString(2, category.getDescription());
            return callst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
    }

    @Override
    public boolean updateCategory(Category category) {
        Connection conn = null;
        CallableStatement callst = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call update_category(?, ?, ?)}");
            callst.setInt(1, category.getId());
            callst.setString(2, category.getName());
            callst.setString(3, category.getDescription());
            return callst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
    }

    @Override
    public boolean deleteCategory(int id) {
        Connection conn = null;
        CallableStatement callst = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call delete_category(?)}");
            callst.setInt(1, id);
            return callst.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
    }

    @Override
    public List<Category> searchCategories(String categoryName) {
        Connection conn = null;
        CallableStatement callst = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call search_category_by_name(?)}");
            callst.setString(1, categoryName);
            rs = callst.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                category.setStatus(rs.getString("status").equalsIgnoreCase("active") ? Status.ACTIVE : Status.INACTIVE);
                categories.add(category);
            }
            return categories;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callst);
        }
        return null;

    }

    @Override
    public boolean checkNameExists(String categoryName) {
        Connection conn = null;
        CallableStatement callst = null;
        ResultSet rs = null;
        try {
            conn = ConnectionDB.openConnection();
            callst = conn.prepareCall("{call check_name_category_exists(?)}");
            callst.setString(1, categoryName);
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
