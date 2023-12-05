package develop;

import java.io.IOException;
import java.util.Scanner;

public class Menu {

    //逻辑控制类
    static MenuController menuController = new MenuController();
    Scanner scanner = new Scanner(System.in);

    //开始界面
    public void welcomeMenu() throws IOException {

        System.out.println();
        System.out.println("              欢迎使用购物管理系统               ");
        System.out.println("***********************************************");
        System.out.println();
        System.out.println("                  1.用户登录");
        System.out.println();
        System.out.println();
        System.out.println("                  2.用户注册");
        System.out.println();
        System.out.println();
        System.out.println("                  3.忘记密码");
        System.out.println();
        System.out.println();
        System.out.println("                  4.管理员登录");
        System.out.println();
        System.out.println();
        System.out.println("                  5.退出系统");
        System.out.println();
        System.out.println("***********************************************");
        System.out.print("请选择您的操作：");
        System.out.println();
        String choice = scanner.next();
        menuController.welcomeMenuController(choice);//跳转控制层
        System.out.println();
        System.out.println();

    }


    public void adminMenu() throws IOException {
        System.out.println();
        System.out.println("                     管理员界面                  ");
        System.out.println("***********************************************");
        System.out.println();
        System.out.println("                  1.管理员密码修改");
        System.out.println();
        System.out.println("                  2.用户密码重置");
        System.out.println();
        System.out.println("                  3.客户管理");
        System.out.println();
        System.out.println("                  4.商品管理");
        System.out.println();
        System.out.println("                  5.注销");
        System.out.println();
        System.out.println("***********************************************");
        System.out.print("请选择您的操作：");
        System.out.println();
        String choice = scanner.next();
        menuController.adminMenuController(choice);//跳转控制层
        System.out.println();
        System.out.println();
    }

    public void userManagerMenu() throws IOException {
        System.out.println();
        System.out.println("                 管理员界面>客户管理              ");
        System.out.println("***********************************************");
        System.out.println();
        System.out.println("                 1.列出所有客户信息");
        System.out.println();
        System.out.println("                 2.删除客户信息");
        System.out.println();
        System.out.println("                 3.查询客户信息");
        System.out.println();
        System.out.println("                 4.返回上一级菜单");
        System.out.println();
        System.out.println("***********************************************");
        System.out.print("请选择您的操作：");
        System.out.println();
        String choice = scanner.next();
        menuController.userManagerController(choice);//跳转控制层
        System.out.println();
        System.out.println();
    }

    public void queryUserMenu() throws IOException {
        System.out.println();
        System.out.println("          管理员界面>客户管理>查询客户信息          ");
        System.out.println("***********************************************");
        System.out.println();
        System.out.println("                 1.按ID查询");
        System.out.println();
        System.out.println("                 2.按用户名查询");
        System.out.println();
        System.out.println("                 3.查询所有客户信息");
        System.out.println();
        System.out.println("                 4.返回上一级菜单");
        System.out.println();
        System.out.println("***********************************************");
        System.out.print("请选择您的操作：");
        System.out.println();
        String choice = scanner.next();
        menuController.queryUserController(choice);//跳转控制层
        System.out.println();
        System.out.println();
    }

    public void goodsMenu() throws IOException {
        System.out.println();
        System.out.println("                管理员界面>商品管理                ");
        System.out.println("***********************************************");
        System.out.println();
        System.out.println("                1.列出所有商品信息");
        System.out.println();
        System.out.println("                2.添加商品信息");
        System.out.println();
        System.out.println("                3.修改商品信息");
        System.out.println();
        System.out.println("                4.删除商品信息");
        System.out.println();
        System.out.println("                5.返回上一级菜单");
        System.out.println();
        System.out.println("***********************************************");
        System.out.print("请选择您的操作：");
        System.out.println();
        String choice = scanner.next();
        menuController.goodsController(choice);//跳转控制层
        System.out.println();
        System.out.println();
    }

    //用户菜单
    public void userMenu() throws IOException {

        System.out.println();
        System.out.println("                    用户已登录                  ");
        System.out.println("***********************************************");
        System.out.println();
        System.out.println("                   1.修改密码");
        System.out.println();
        System.out.println("                   2.购物管理");
        System.out.println();
        System.out.println("                   3.注销");
        System.out.println();
        System.out.println("***********************************************");
        System.out.print("请选择您的操作：");
        System.out.println();
        String choice = scanner.next();
        menuController.userMenuController(choice);//跳转控制层
        System.out.println();
        System.out.println();
    }

    public void shoppingMenu() throws IOException {
        System.out.println();
        System.out.println("                用户界面>购物管理              ");
        System.out.println("***********************************************");
        System.out.println();
        System.out.println("                 1.添加购物车");
        System.out.println();
        System.out.println("                 2.移除购物车");
        System.out.println();
        System.out.println("                 3.修改购买数量");
        System.out.println();
        System.out.println("                 4.购物车结算");
        System.out.println();
        System.out.println("                 5.查看购物历史");
        System.out.println();
        System.out.println("                 6.返回上一级菜单");
        System.out.println();
        System.out.println("***********************************************");
        System.out.print("请选择您的操作：");
        System.out.println();
        String choice = scanner.next();
        menuController.shoppingController(choice);//跳转控制层
        System.out.println();
        System.out.println();
    }

}
