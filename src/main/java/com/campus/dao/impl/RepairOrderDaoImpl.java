package com.campus.dao.impl;

import com.campus.dao.RepairOrderDao;
import com.campus.entity.RepairOrder;
import com.campus.utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairOrderDaoImpl implements RepairOrderDao {
    @Override
    public boolean save(RepairOrder order) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "INSERT INTO repair_orders (user_id, title, description, location, status) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, order.getUserId());
            stmt.setString(2, order.getTitle());
            stmt.setString(3, order.getDescription());
            stmt.setString(4, order.getLocation());
            stmt.setString(5, order.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.closeResources(conn, stmt, null);
        }
    }

    @Override
    public boolean update(RepairOrder order) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE repair_orders SET status = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, order.getStatus());
            stmt.setInt(2, order.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.closeResources(conn, stmt, null);
        }
    }

    @Override
    public RepairOrder findById(Integer id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM repair_orders WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return extractRepairOrderFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, stmt, rs);
        }
        return null;
    }

    @Override
    public List<RepairOrder> findAll() {
        List<RepairOrder> orders = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM repair_orders ORDER BY create_time DESC");

            while (rs.next()) {
                orders.add(extractRepairOrderFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, stmt, rs);
        }
        return orders;
    }

    @Override
    public List<RepairOrder> findByUserId(Integer userId) {
        List<RepairOrder> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM repair_orders WHERE user_id = ? ORDER BY create_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(extractRepairOrderFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, stmt, rs);
        }
        return orders;
    }

    @Override
    public List<RepairOrder> findByStatus(String status) {
        List<RepairOrder> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM repair_orders WHERE status = ? ORDER BY create_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(extractRepairOrderFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, stmt, rs);
        }
        return orders;
    }

    private RepairOrder extractRepairOrderFromResultSet(ResultSet rs) throws SQLException {
        RepairOrder order = new RepairOrder();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTitle(rs.getString("title"));
        order.setDescription(rs.getString("description"));
        order.setLocation(rs.getString("location"));
        order.setStatus(rs.getString("status"));
        order.setCreateTime(rs.getTimestamp("create_time"));
        order.setUpdateTime(rs.getTimestamp("update_time"));
        return order;
    }
}