package develop;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

import static develop.UserController.userFilePath;


public class AdminController {
    public static String adminFilePath = ".\\src\\develop\\Admin.csv";

    CsvManager adminFile = new CsvManager(adminFilePath);
    CsvManager userFile = new CsvManager(userFilePath);
    String resetPassword = "88888888";
    Scanner scanner = new Scanner(System.in);

    public Admin getAdmin(String username, String password) throws IOException {
        Admin admin = null;
        ArrayList<String[]> adminList = adminFile.read();
        for (String[] row : adminList) {
            if (username.equals(row[0]) && password.equals(row[1])) {
                admin = new Admin();
                admin.setUsername(row[0]);
                admin.setPassword(row[1]);
                break;
            }

        }
        return admin;

    }

    public void changePassword() throws IOException {

        System.out.println("请输入要修改的管理员用户名:");
        String adminName = scanner.next();
        System.out.println("请输入旧密码:");
        String oldPwd = scanner.next();

        ArrayList<String[]> adminList = adminFile.read();
        for (String[] row : adminList) {
            if (adminName.equals(row[0]) && oldPwd.equals(row[1])) {

                System.out.println("请输入新密码:");
                String newPwd = scanner.next();

                row[1] = newPwd;
                adminFile.rewrite(adminList);
                System.out.println("修改成功");
                return;
            }
        }

        System.out.println("用户名或密码错误");
    }

    public void resetUserPassword(String phoneNum) throws IOException {
        ArrayList<String[]> userList = userFile.read();
        for (String[] row : userList) {
            if (phoneNum.equals(row[5])) {
                row[7] = resetPassword;
                userFile.rewrite(userList);
                System.out.println("密码重置为 "+ resetPassword);
                return;
            }
        }
        System.out.println("用户手机号不存在");
    }
}


