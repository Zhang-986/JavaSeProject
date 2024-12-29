package com.campus.service;

import com.campus.entity.RepairOrder;
import java.util.List;

public interface RepairOrderService {
    boolean submitRepairOrder(RepairOrder order);
    boolean updateOrderStatus(Integer orderId, String status);
    List<RepairOrder> getAllOrders();
    List<RepairOrder> getUserOrders(Integer userId);
}