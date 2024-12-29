package com.campus.service.impl;

import com.campus.dao.RepairRecordDao;
import com.campus.dao.impl.RepairRecordDaoImpl;
import com.campus.entity.RepairRecord;
import com.campus.service.RepairRecordService;

public class RepairRecordServiceImpl implements RepairRecordService {
    private final RepairRecordDao repairRecordDao = new RepairRecordDaoImpl();
    @Override
    public boolean save(RepairRecord record) {
        return repairRecordDao.save(record);
    }
}
