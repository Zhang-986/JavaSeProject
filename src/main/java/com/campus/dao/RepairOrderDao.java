package com.campus.dao;

import com.campus.entity.RepairOrder;
import java.util.List;

public interface RepairOrderDao {
    boolean save(RepairOrder order);
    boolean update(RepairOrder order);
    RepairOrder findById(Integer id);
    List<RepairOrder> findAll();
    List<RepairOrder> findByUserId(Integer userId);
} 