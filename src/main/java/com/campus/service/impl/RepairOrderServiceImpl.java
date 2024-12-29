package com.campus.service.impl;

import com.campus.dao.RepairOrderDao;
import com.campus.dao.impl.RepairOrderDaoImpl;
import com.campus.entity.RepairOrder;
import com.campus.service.RepairOrderService;

import java.util.List;

public class RepairOrderServiceImpl implements RepairOrderService {
    private final RepairOrderDao repairOrderDao = new RepairOrderDaoImpl();

    @Override
    public boolean submitRepairOrder(RepairOrder order) {
        return repairOrderDao.save(order);
    }

    @Override
    public boolean updateOrderStatus(Integer orderId, String status) {
        RepairOrder order = repairOrderDao.findById(orderId);
        if (order != null) {
            order.setStatus(status);
            return repairOrderDao.update(order);
        }
        return false;
    }



    @Override
    public List<RepairOrder> getAllOrders() {
        return repairOrderDao.findAll();
    }

    @Override
    public List<RepairOrder> getUserOrders(Integer userId) {
        return repairOrderDao.findByUserId(userId);
    }

}