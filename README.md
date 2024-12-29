# 智慧校园后勤报修系统

# 第一章 需求分析

## 1.1 课程设计题目

本系统实现了一个基于Java控制台的校园后勤报修系统,通过标准输入输出实现用户交互,使用MySQL数据库存储数据,采用DAO设计模式实现数据访问层,具有良好的可扩展性和维护性。 

## 1.2 课程设计任务及要求

本系统主要实现以下功能:

1. **用户管理功能**
   - 用户注册与登录：实现新用户注册和已有用户登录
   - 角色权限控制：区分普通用户(user)和管理员(admin)权限
   - 用户信息管理：包括用户基本信息的维护

2. **报修管理功能**
   - 提交报修申请：用户可提交包含标题、描述、位置等信息的报修单
   - 查看报修状态：实时查看报修单处理进度
   - 维修进度跟踪：记录维修过程中的各个状态变更
   - 报修记录查询：支持查看历史报修记录

3. **后台管理功能**
   - 工单处理管理：管理员可以查看和处理报修工单
   - 维修记录管理：记录维修过程和结果
   - 用户信息管理：管理用户账号和权限

## 1.3 开发环境

1. **开发工具**
   - IDE: IntelliJ IDEA 2023
   - 项目管理: Maven 3.8
   - 版本控制: Git

2. **运行环境**
   - 操作系统: Windows 10/11
   - JDK版本: 17
   - 数据库: MySQL 8.0.38

3. **项目依赖**

```xml
<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.27</version>
    </dependency>
</dependencies>
```

## 1.4 系统功能需求

1. **基础功能需求**
   - 用户管理：注册、登录、权限控制
   - 报修管理：提交、查询、状态更新
   - 维修管理：处理工单、记录维修过程
   - 数据统计：报修记录查询统计

2. **系统性能需求**
   - 响应时间：操作响应时间<1秒
   - 并发处理：支持多用户同时操作
   - 数据一致：确保数据的准确性和一致性
   - 易用性：操作界面简单直观

# 第二章 系统设计

## 2.1 系统架构设计

1. **三层架构**
   - 数据访问层(DAO)：直接操作数据库
   - 业务逻辑层(Service)：实现业务逻辑
   - 表现层(View)：负责与用户交互

2. **设计模式**
   - DAO模式：分离数据访问和业务逻辑
   - 单例模式：数据库连接池
   - 工厂模式：创建DAO实现类

## 2.2 数据库设计

1. **users表 - 用户信息表**

```sql
CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY (username)
);
```

2. **repair_orders表 - 报修工单表**

```sql
CREATE TABLE repair_orders (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT DEFAULT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(100),
    status VARCHAR(20) DEFAULT 'pending',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
```

3. **repair_records表 - 维修记录表**

```sql
CREATE TABLE repair_records (
    id INT NOT NULL AUTO_INCREMENT,
    order_id INT DEFAULT NULL,
    handler_id INT DEFAULT NULL,
    handling_notes TEXT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
```

## 2.3 核心接口设计

1. **数据访问层接口**

```java
public interface UserDao {
    User findByUsername(String username);
    boolean save(User user);
}

public interface RepairOrderDao {
    boolean save(RepairOrder order);
    boolean update(RepairOrder order);
    RepairOrder findById(Integer id);
    List<RepairOrder> findAll();
    List<RepairOrder> findByUserId(Integer userId);
}

public interface RepairRecordDao {
    boolean save(RepairRecord record);
}
```

2. **业务逻辑层接口**

```java
public interface UserService {
    boolean register(String username, String password);
    User login(String username, String password);
}

public interface RepairOrderService {
    boolean submitOrder(RepairOrder order);
    boolean updateStatus(Integer orderId, String status);
    List<RepairOrder> queryOrders(Integer userId);
}
```

# 第三章 详细设计

## 3.1 数据访问层设计

### 3.1.1 数据库连接工具(JDBCUtils)

- getConnection(): 获取数据库连接
- closeResources(): 关闭数据库资源

### 3.1.2 用户数据访问(UserDao)

- findByUsername(): 根据用户名查询用户信息
- save(): 保存新用户信息

### 3.1.3 报修工单数据访问(RepairOrderDao)

- save(): 保存报修工单
- update(): 更新工单状态
- findById(): 查询单个工单
- findAll(): 查询所有工单
- findByUserId(): 查询用户的报修记录

### 3.1.4 维修记录数据访问(RepairRecordDao)

- save(): 保存维修处理记录

## 3.2 业务逻辑层设计

### 3.2.1 用户服务(UserService)

- login(): 用户登录验证
- register(): 新用户注册

### 3.2.2 报修工单服务(RepairOrderService)

- submitOrder(): 提交报修工单
- updateStatus(): 更新工单状态
- queryOrders(): 查询报修记录

### 3.2.3 维修记录服务(RepairRecordService)

- addRecord(): 添加维修记录
- queryRecords(): 查询维修记录

## 3.3 表现层设计

### 3.3.1 主界面功能(MainView)

1. 初始菜单
   - 用户登录
   - 用户注册
   - 退出系统

2. 用户功能菜单
   - 提交报修
   - 查看报修记录
   - 查看维修进度
   - 退出登录

3. 管理员功能菜单
   - 查看所有工单
   - 处理报修工单
   - 添加维修记录
   - 退出登录

## 3.4 核心业务流程

1. **用户注册登录流程**

```
用户输入 -> 验证信息 -> 数据库操作 -> 返回结果
```

2. **报修工单流程**

```
提交报修 -> 生成工单 -> 保存数据库 -> 返回结果
查询报修 -> 获取记录 -> 显示结果
```

3. **维修处理流程**

```
选择工单 -> 更新状态 -> 记录处理 -> 完成维修
```

# 第四章 系统测试

## 4.1 测试环境

1. **硬件环境**
   - 处理器: Intel Core i7
   - 内存: 32GB
   - 硬盘: 1T SSD

2. **软件环境**
   - 操作系统: Windows 11
   - JDK: 17
   - MySQL: 8.0.38
   - IDEA: 2023.2

## 4.2 功能测试

### 4.2.1 用户管理测试

| 测试项目 | 测试内容     | 预期结果     | 实际结果 |
| -------- | ------------ | ------------ | -------- |
| 用户注册 | 新用户注册   | 创建成功     | 通过     |
| 用户登录 | 已有用户登录 | 登录成功     | 通过     |
| 权限控制 | 角色权限验证 | 权限区分正确 | 通过     |

### 4.2.2 报修功能测试

| 测试项目 | 测试内容     | 预期结果 | 实际结果 |
| -------- | ------------ | -------- | -------- |
| 提交报修 | 创建报修单   | 提交成功 | 通过     |
| 查询报修 | 查看报修记录 | 显示正确 | 通过     |
| 处理报修 | 更新维修状态 | 更新成功 | 通过     |

## 4.3 测试结果分析

1. **功能完整性**
   - 基本功能全部实现
   - 操作流程清晰
   - 数据处理准确

2. **系统稳定性**
   - 运行稳定
   - 异常处理完善
   - 数据一致性好

# 第五章 总结与展望

## 5.1 项目总结

1. **技术特点**
   - 采用三层架构
   - 使用DAO设计模式
   - 实现MVC分离

2. **实现功能**
   - 用户管理系统
   - 报修工单处理
   - 维修记录管理

3. **项目亮点**
   - 结构清晰
   - 扩展性好
   - 维护方便

## 5.2 心得体会

1. **技术收获**
   - 掌握Java编程
   - 理解设计模式
   - 熟悉数据库操作

2. **能力提升**
   - 项目开发经验
   - 问题解决能力
   - 文档编写能力 

# 参考文献

[1] Bruce Eckel. Java编程思想(第4版). 机械工业出版社, 2007

[2] Martin Fowler. 企业应用架构模式. 机械工业出版社, 2006

[3] MySQL 8.0参考手册. https://dev.mysql.com/doc/refman/8.0/en/

[4] Java SE 17文档. https://docs.oracle.com/en/java/javase/17/


