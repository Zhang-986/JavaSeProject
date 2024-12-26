package com.campus.entity;

import java.util.Date;

public class RepairRecord {
    private Integer id;
    private Integer orderId;
    private Integer handlerId;
    private String handlingNotes;
    private Date createTime;

    // 构造器
    public RepairRecord() {}

    public RepairRecord(Integer orderId, Integer handlerId, String handlingNotes) {
        this.orderId = orderId;
        this.handlerId = handlerId;
        this.handlingNotes = handlingNotes;
    }

    // Getter和Setter方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getHandlerId() { return handlerId; }
    public void setHandlerId(Integer handlerId) { this.handlerId = handlerId; }
    public String getHandlingNotes() { return handlingNotes; }
    public void setHandlingNotes(String handlingNotes) { this.handlingNotes = handlingNotes; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
} 