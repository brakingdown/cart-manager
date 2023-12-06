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
        boolean isUpdated = dbManager.updateUserPasswordByPhone(phoneNum, resetPassword);

        if (isUpdated) {
            System.out.println("密码已重置为: " + resetPassword);
        } else {
            System.out.println("用户手机号不存在，密码重置失败");
        }
    }

}