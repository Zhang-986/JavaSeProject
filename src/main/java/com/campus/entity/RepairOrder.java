package com.campus.entity;

import java.util.Date;

public class RepairOrder {
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private String location;
    private String status;
    private Date createTime;
    private Date updateTime;

    // 构造器
    public RepairOrder() {}

    public RepairOrder(Integer userId, String title, String description, String location) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.status = "pending";
    }

    // Getter和Setter方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}