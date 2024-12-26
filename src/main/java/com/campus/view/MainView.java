package com.campus.view;

import com.campus.dao.impl.RepairRecordDaoImpl;
import com.campus.entity.RepairOrder;
import com.campus.entity.User;
import com.campus.service.RepairOrderService;
import com.campus.service.UserService;
import com.campus.service.impl.RepairOrderServiceImpl;
import com.campus.service.impl.UserServiceImpl;
import com.campus.entity.RepairRecord;
import com.campus.dao.RepairRecordDao;

import java.util.List;
import java.util.Scanner;

public class MainView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserServiceImpl();
    private static final RepairOrderService repairOrderService = new RepairOrderServiceImpl();
    private static User currentUser;
    private static final RepairRecordDao repairRecordDao = new RepairRecordDaoImpl();

    public static void main(String[] args) {
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else if ("admin".equals(currentUser.getRole())) {
                showAdminMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n=== 校园报修系统 ===");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("3. 退出");
        System.out.print("请选择：");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 消费换行符

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("感谢使用，再见！");
                System.exit(0);
            default:
                System.out.println("无效选择，请重试。");
        }
    }

    private static void showUserMenu() {
        System.out.println("\n=== 用户菜单 ===");
        System.out.println("1. 提交报修");
        System.out.println("2. 查看我的报修");
        System.out.println("3. 退出登录");
        System.out.print("请选择：");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 消费换行符

        switch (choice) {
            case 1:
                submitRepair();
                break;
            case 2:
                viewMyRepairs();
                break;
            case 3:
                logout();
                break;
            default:
                System.out.println("无效选择，请重试。");
        }
    }

    private static void showAdminMenu() {
        System.out.println("\n=== 管理员菜单 ===");
        System.out.println("1. 查看所有报修");
        System.out.println("2. 处理报修");
        System.out.println("3. 查看统计信息");
        System.out.println("4. 退出登录");
        System.out.print("请选择：");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 消费换行符

        switch (choice) {
            case 1:
                viewAllRepairs();
                break;
            case 2:
                handleRepair();
                break;
            case 3:
                showStatistics();
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("无效选择，请重试。");
        }
    }

    private static void login() {
        System.out.print("用户名：");
        String username = scanner.nextLine();
        System.out.print("密码：");
        String password = scanner.nextLine();

        currentUser = userService.login(username, password);
        if (currentUser != null) {
            System.out.println("登录成功！");
        } else {
            System.out.println("登录失败，用户名或密码错误。");
        }
    }

    private static void register() {
        System.out.print("用户名：");
        String username = scanner.nextLine();
        System.out.print("密码：");
        String password = scanner.nextLine();

        if (userService.register(username, password)) {
            System.out.println("注册成功！");
        } else {
            System.out.println("注册失败，用户名可能已存在。");
        }
    }

    private static void submitRepair() {
        System.out.print("报修标题：");
        String title = scanner.nextLine();
        System.out.print("问题描述：");
        String description = scanner.nextLine();
        System.out.print("位置：");
        String location = scanner.nextLine();

        RepairOrder order = new RepairOrder(currentUser.getId(), title, description, location);
        if (repairOrderService.submitRepairOrder(order)) {
            System.out.println("报修提交成功！");
        } else {
            System.out.println("报修提交失败，请重试。");
        }
    }

    private static void viewMyRepairs() {
        List<RepairOrder> orders = repairOrderService.getUserOrders(currentUser.getId());
        if (orders.isEmpty()) {
            System.out.println("暂无报修记录。");
            return;
        }

        System.out.println("\n=== 我的报修记录 ===");
        for (RepairOrder order : orders) {
            System.out.println("ID: " + order.getId());
            System.out.println("标题: " + order.getTitle());
            System.out.println("状态: " + order.getStatus());
            System.out.println("提交时间: " + order.getCreateTime());
            System.out.println("------------------------");
        }
    }

    private static void viewAllRepairs() {
        List<RepairOrder> orders = repairOrderService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("暂无报修记录。");
            return;
        }

        System.out.println("\n=== 所有报修记录 ===");
        for (RepairOrder order : orders) {
            System.out.println("ID: " + order.getId());
            System.out.println("用户ID: " + order.getUserId());
            System.out.println("标题: " + order.getTitle());
            System.out.println("状态: " + order.getStatus());
            System.out.println("提交时间: " + order.getCreateTime());
            System.out.println("------------------------");
        }
    }

    private static void handleRepair() {
        System.out.print("请输入要处理的报修ID：");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // 消费换行符

        System.out.println("请选择处理状态：");
        System.out.println("1. 处理中");
        System.out.println("2. 已完成");
        int choice = scanner.nextInt();
        scanner.nextLine(); // 消费换行符

        System.out.print("请输入处理意见：");
        String handlingNotes = scanner.nextLine();

        String status = choice == 1 ? "processing" : "completed";
        
        // 更新报修单状态
        if (repairOrderService.updateOrderStatus(orderId, status)) {
            // 添加处理记录
            RepairRecord record = new RepairRecord(orderId, currentUser.getId(), handlingNotes);
            if (repairRecordDao.save(record)) {
                System.out.println("处理成功！");
            } else {
                System.out.println("处理记录保存失败。");
            }
        } else {
            System.out.println("状态更新失败，请检查报修ID是否正确。");
        }
    }

    private static void showStatistics() {
        List<RepairOrder> allOrders = repairOrderService.getAllOrders();
        int pendingCount = 0;
        int processingCount = 0;
        int completedCount = 0;

        for (RepairOrder order : allOrders) {
            switch (order.getStatus()) {
                case "pending":
                    pendingCount++;
                    break;
                case "processing":
                    processingCount++;
                    break;
                case "completed":
                    completedCount++;
                    break;
            }
        }

        System.out.println("\n=== 报修统计信息 ===");
        System.out.println("待处理: " + pendingCount);
        System.out.println("处理中: " + processingCount);
        System.out.println("已完成: " + completedCount);
        System.out.println("总计: " + allOrders.size());
    }

    private static void logout() {
        currentUser = null;
        System.out.println("已退出登录。");
    }
}