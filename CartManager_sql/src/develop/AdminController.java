package develop;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class AdminController {
    DatabaseManager dbManager = new DatabaseManager();
    String resetPassword = "88888888";
    Scanner scanner = new Scanner(System.in);

    public Admin getAdmin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE 管理员用户名 = ? AND 管理员密码 = ?";
        ArrayList<String[]> adminList = dbManager.read(sql, username, password);
        if (!adminList.isEmpty()) {
            String[] row = adminList.get(0);
            Admin admin = new Admin();
            admin.setUsername(row[0]);
            admin.setPassword(row[1]);
            return admin;
        }
        return null;
    }

    public void changePassword() throws SQLException {
        System.out.println("请输入要修改的管理员用户名:");
        String adminName = scanner.next();
        System.out.println("请输入旧密码:");
        String oldPwd = scanner.next();

        Admin admin = getAdmin(adminName, oldPwd);
        if (admin != null) {
            System.out.println("请输入新密码:");
            String newPwd = scanner.next();

            dbManager.updateAdmin(adminName, newPwd);
            System.out.println("修改成功");
        } else {
            System.out.println("用户名或密码错误");
        }
    }

    public void resetUserPassword(String phoneNum) throws SQLException {
        String UserSql = "SELECT * FROM User WHERE 用户手机号 = ?";
        ArrayList<String[]> userList = dbManager.read(UserSql, phoneNum);
        if (!userList.isEmpty()) {
            String[] user = userList.get(0);
            Long id = Long.parseLong(user[0]); // 假设客户ID是第一个字段

            dbManager.updateUser(id, user[1], user[2], user[3], new BigDecimal(user[4]), phoneNum, user[6], resetPassword);
            System.out.println("密码重置为 " + resetPassword);
        } else {
            System.out.println("用户手机号不存在");
        }
    }
}

/*
public class AdminController {
    DatabaseManager dbManager = new DatabaseManager();
    String resetPassword = "88888888";
    Scanner scanner = new Scanner(System.in);

    public Admin getAdmin(String username, String password) {
        Admin admin = null;
        String sql = "SELECT * FROM Admin";
        try {
            ArrayList<String[]> adminList = dbManager.read(sql); // 这里需要修改以适配 DatabaseManager 类的实际实现
            if (!adminList.isEmpty()) {
                String[] row = adminList.get(0);
                admin = new Admin();
                admin.setUsername(row[0]);
                admin.setPassword(row[1]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public void changePassword() {
        System.out.println("请输入要修改的管理员用户名:");
        String adminName = scanner.next();
        System.out.println("请输入旧密码:");
        String oldPwd = scanner.next();

        String sql = "UPDATE Admin SET 管理员密码 = ? WHERE 管理员用户名 = ? AND 管理员密码 = ?";
        try {
            System.out.println("请输入新密码:");
            String newPwd = scanner.next();

            dbManager.updateAdmin(adminName, newPwd); // 适配 DatabaseManager 的 updateAdmin 方法
            System.out.println("修改成功");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户名或密码错误");
        }
    }

    public void resetUserPassword(String phoneNum) {
        try {
            dbManager.updateUserPasswordByPhone(phoneNum, resetPassword); // 这里假设 DatabaseManager 有一个更新密码的方法
            System.out.println("密码重置为 " + resetPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("用户手机号不存在");
        }
    }
}
*/