package com.campus.dao.impl;

import com.campus.dao.RepairRecordDao;
import com.campus.entity.RepairRecord;
import com.campus.utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairRecordDaoImpl implements RepairRecordDao {
    @Override
    public boolean save(RepairRecord record) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "INSERT INTO repair_records (order_id, handler_id, handling_notes) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, record.getOrderId());
            stmt.setInt(2, record.getHandlerId());
            stmt.setString(3, record.getHandlingNotes());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.closeResources(conn, stmt, null);
        }
    }

    @Override
    public List<RepairRecord> findByOrderId(Integer orderId) {
        List<RepairRecord> records = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM repair_records WHERE order_id = ? ORDER BY create_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                records.add(extractRepairRecordFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, stmt, rs);
        }
        return records;
    }

    @Override
    public List<RepairRecord> findByHandlerId(Integer handlerId) {
        List<RepairRecord> records = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM repair_records WHERE handler_id = ? ORDER BY create_time DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, handlerId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                records.add(extractRepairRecordFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResources(conn, stmt, rs);
        }
        return records;
    }

    private RepairRecord extractRepairRecordFromResultSet(ResultSet rs) throws SQLException {
        RepairRecord record = new RepairRecord();
        record.setId(rs.getInt("id"));
        record.setOrderId(rs.getInt("order_id"));
        record.setHandlerId(rs.getInt("handler_id"));
        record.setHandlingNotes(rs.getString("handling_notes"));
        record.setCreateTime(rs.getTimestamp("create_time"));
        return record;
    }
} 