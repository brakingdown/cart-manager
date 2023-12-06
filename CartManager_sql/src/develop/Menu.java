package develop;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    static MenuController menuController = new MenuController();
    Scanner scanner = new Scanner(System.in);

    private void printMenu(String header, String... options) {
        System.out.println("\n" + header);
        System.out.println("***********************************************");
        for (String option : options) {
            System.out.println("\n                  " + option);
        }
        System.out.println("\n***********************************************");
        System.out.print("请选择您的操作：\n");
    }

    private String getUserChoice() {
        return scanner.next();
    }

    public void welcomeMenu() throws SQLException {
        printMenu("欢迎使用购物管理系统", "1.用户登录", "2.用户注册", "3.忘记密码", "4.管理员登录", "5.退出系统");
        String choice = getUserChoice();
        menuController.welcomeMenuController(choice);
    }

    public void adminMenu() throws SQLException {
        printMenu("管理员界面", "1.管理员密码修改", "2.用户密码重置", "3.客户管理", "4.商品管理", "5.注销");
        String choice = getUserChoice();
        menuController.adminMenuController(choice);
    }

    // UserManager 菜单
    public void userManagerMenu() throws SQLException {
        printMenu("管理员界面>客户管理",
                "1.列出所有客户信息",
                "2.删除客户信息",
                "3.查询客户信息",
                "4.返回上一级菜单");
        String choice = getUserChoice();
        menuController.userManagerController(choice);
    }

    // QueryUser 菜单
    public void queryUserMenu() throws SQLException {
        printMenu("管理员界面>客户管理>查询客户信息",
                "1.按ID查询",
                "2.按用户名查询",
                "3.查询所有客户信息",
                "4.返回上一级菜单");
        String choice = getUserChoice();
        menuController.queryUserController(choice);
    }

    // Goods 菜单
    public void goodsMenu() throws SQLException {
        printMenu("管理员界面>商品管理",
                "1.列出所有商品信息",
                "2.添加商品信息",
                "3.修改商品信息",
                "4.删除商品信息",
                "5.返回上一级菜单");
        String choice = getUserChoice();
        menuController.goodsController(choice);
    }

    // User 菜单
    public void userMenu() throws SQLException {
        printMenu("用户已登录",
                "1.修改密码",
                "2.购物管理",
                "3.注销");
        String choice = getUserChoice();
        menuController.userMenuController(choice);
    }

    // Shopping 菜单
    public void shoppingMenu() throws SQLException {
        printMenu("用户界面>购物管理",
                "1.添加购物车",
                "2.移除购物车",
                "3.修改购买数量",
                "4.购物车结算",
                "5.查看购物历史",
                "6.返回上一级菜单");
        String choice = getUserChoice();
        menuController.shoppingController(choice);
    }

}

