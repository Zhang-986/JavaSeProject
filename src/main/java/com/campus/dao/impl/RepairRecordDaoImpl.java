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


} 