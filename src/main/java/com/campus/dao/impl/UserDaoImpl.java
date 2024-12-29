package com.campus.dao.impl;

import com.campus.dao.UserDao;
import com.campus.entity.User;
import com.campus.utils.JDBCUtils;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    @Override
    public User findByUsername(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM users WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, stmt, rs);
        }
        return null;
    }

    @Override
    public boolean save(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.closeResources(conn, stmt, null);
        }
    }

}