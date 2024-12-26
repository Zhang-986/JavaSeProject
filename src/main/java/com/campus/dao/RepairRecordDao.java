package com.campus.dao;

import com.campus.entity.RepairRecord;
import java.util.List;

public interface RepairRecordDao {
    boolean save(RepairRecord record);
    List<RepairRecord> findByOrderId(Integer orderId);
    List<RepairRecord> findByHandlerId(Integer handlerId);
} 