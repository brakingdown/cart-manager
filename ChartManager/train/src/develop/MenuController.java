package develop;

import java.io.IOException;
import java.util.Scanner;

public class MenuController {

    UserController userController = new UserController();
    AdminController adminController = new AdminController();
    GoodsController goodsController = new GoodsController();
    Password pwdClass = new Password();
    static Menu menu = new Menu();
    User user = new User();
    int RANDOM_PWD_LEN = 8;
    ShoppingCartController shoppingCartController = new ShoppingCartController();

    Scanner scanner = new Scanner(System.in);

    //欢迎页控制层
    public void welcomeMenuController(String choice) throws IOException {

        switch (choice) {
            case "1" -> {

                //用户登录
                int i = 5;  //拥有5次登录的机会


                while (i > 0) {
                    System.out.println("请输入用户名");
                    String username = scanner.next();
                    System.out.println("请输入密码:");
                    String password = scanner.next();

                    user = userController.getUser(username, password);

                    if (user != null) {
                        menu.userMenu();
                        return;
                    } else {
                        i--;
                        System.out.println("用户名或密码错误，你还有：" + i + "次机会！");
                    }
                }
                System.out.println("错误次数已达上限，退出系统");
                System.exit(0);
            }
            case "2"->{
                System.out.println();
                System.out.println("                  用户注册界面                  ");
                System.out.println("***********************************************");
                userController.userRegister();//注册
            }
            case"3"->{
                System.out.println();
                System.out.println("                    忘记密码                    ");
                System.out.println("***********************************************");
                System.out.println("请输入用户名:");
                String username = scanner.next();
                System.out.println("请输入注册邮箱:");
                String email = scanner.next();
                User u = userController.getUserByUsernameAndEmail(username, email);
                if(u!=null){
                    String newPwd = pwdClass.getRandomPassword(RANDOM_PWD_LEN);
                    u.setPassword(newPwd);
                    userController.modifyUser(userController.getUserString(u));
                    System.out.println("新密码已发送至邮箱:" + " " + newPwd);
                }
                else {
                    System.out.println("用户名或密码错误");
                }
                scanner.nextLine();
            }

            case "4" -> {
                int i = 5;

                while (i > 0) {
                    System.out.println("请输入管理员账号:");
                    String username = scanner.next();
                    System.out.println("请输入管理员密码:");
                    String password = scanner.next();
                    Admin admin = adminController.getAdmin(username, password);

                    if (admin != null) {
                        menu.adminMenu();
                        return;
                    } else {
                        i--;
                        System.out.println("账号或密码错误，你还有：" + i + "次机会！");
                    }
                }
                System.out.println("错误次数已达上限，退出系统");
                System.exit(0);
            }
            case "5" -> {
                System.out.println("     退出系统");
                //退出系统
                System.exit(0);
            }
            default -> {
                //错误操作
                System.out.println("您输入的操作有误，请重新选择");
            }
        }
        System.out.println();
        System.out.println("键入回车返回主界面...");
        scanner.nextLine();
        menu.welcomeMenu();
    }


    public void adminMenuController(String choice) throws IOException{

        switch (choice) {
            case "1" -> {
                System.out.println();
                System.out.println("              管理员界面>管理员密码修改            ");
                System.out.println("***********************************************");
                adminController.changePassword();
            }
            case "2" -> {
                System.out.println();
                System.out.println("               管理员界面>用户密码重置             ");
                System.out.println("***********************************************");
                System.out.println("请输入要重置密码的用户手机号:");
                String phoneNum = scanner.next();
                adminController.resetUserPassword(phoneNum);
                scanner.nextLine();
            }
            case "3" -> {
                menu.userManagerMenu();
                return;
            }
            case "4" -> {
                menu.goodsMenu();
                return;
            }
            case "5" -> {
                menu.welcomeMenu();
                return;
            }
            default -> {
                //错误操作
                System.out.println("您输入的操作有误，请重新选择");
            }
        }
        System.out.println();
        System.out.println("键入回车返回管理员界面...");
        scanner.nextLine();
        menu.adminMenu();

    }

    public void userManagerController(String choice) throws IOException {
        switch (choice) {
            case "1" -> {
                System.out.println();
                System.out.println("         管理员界面>客户管理>列出所有用户信息         ");
                System.out.println("***********************************************");
                userController.showAllUsers();
            }
            case "2" -> {
                System.out.println();
                System.out.println("          管理员界面>客户管理>删除客户信息          ");
                System.out.println("***********************************************");
                System.out.println("请输入要删除客户的手机号");
                userController.deleteUser(scanner.next());
            }
            case "3" -> {
                menu.queryUserMenu();
                return;
            }
            case "4" -> {
                menu.adminMenu();
                return;
            }
            default -> {
                System.out.println("您输入的操作有误，请重新选择");
            }
        }
        System.out.println();
        System.out.println("键入回车返回客户管理界面");
        scanner.nextLine();
        scanner.nextLine();
        menu.userManagerMenu();
    }

    public void queryUserController(String choice) throws IOException {
        switch (choice) {
            case "1" -> {
                System.out.println();
                System.out.println("      管理员界面>客户管理>查询客户信息>按ID查询       ");
                System.out.println("***********************************************");
                System.out.println("请输入客户ID");
                userController.showUser(userController.getUserById(scanner.next()));
            }
            case "2" -> {
                System.out.println();
                System.out.println("     管理员界面>客户管理>查询客户信息>按用户名查询     ");
                System.out.println("***********************************************");
                System.out.println("请输入客户用户名");
                userController.showUser(userController.getUserByUsername(scanner.next()));
            }
            case "3" -> {
                System.out.println("    管理员界面>客户管理>查询客户信息>查询所有客户信息   ");
                System.out.println("***********************************************");
                userController.showAllUsers();
            }
            case "4" -> {
                menu.userManagerMenu();
                return;
            }
            default -> {
                //错误操作
                System.out.println("您输入的操作有误，请重新选择");
            }
        }
        System.out.println();
        System.out.println("键入回车返回查询界面");
        scanner.nextLine();
        scanner.nextLine();
        menu.queryUserMenu();

    }

    public void goodsController(String choice) throws IOException {
        switch (choice) {
            case "1" -> {
                System.out.println();
                System.out.println("        管理员界面>商品管理>列出所有商品信息        ");
                System.out.println("***********************************************");
                goodsController.showAllGoods();
            }
            case "2" -> {
                System.out.println();
                System.out.println("          管理员界面>商品管理>添加商品信息          ");
                System.out.println("***********************************************");
                goodsController.addGoods();
            }
            case "3" -> {
                System.out.println();
                System.out.println("          管理员界面>商品管理>修改商品信息          ");
                System.out.println("***********************************************");
                System.out.println("在库商品:");
                System.out.println();
                goodsController.showAllGoods();
                System.out.println();
                int flag = goodsController.modifyGoods();
                if (flag == 1){
                    System.out.println("修改成功");
                }
                else if(flag == 0){
                    System.out.println("id不存在");
                }
            }
            case "4" -> {
                System.out.println();
                System.out.println("          管理员界面>商品管理>删除商品信息          ");
                System.out.println("***********************************************");
                System.out.println("在库商品:");
                System.out.println();
                goodsController.showAllGoods();
                System.out.println("请输入要删除的商品ID");
                goodsController.deleteGoods(scanner.next());
            }
            case "5" -> {
                menu.adminMenu();
                return;
            }
            default -> {
                System.out.println("您输入的操作有误，请重新选择");
            }
        }
        System.out.println();
        System.out.println("键入回车返回商品管理界面");
        scanner.nextLine();
        scanner.nextLine();
        menu.goodsMenu();
    }

    public void userMenuController(String choice) throws IOException{

        switch (choice) {
            case "1" -> {
                System.out.println();
                System.out.println("                用户界面>修改密码                ");
                System.out.println("***********************************************");
                System.out.println("请输入旧密码,键入e返回用户界面:");
                String oldPwd;
                while (true) {
                    oldPwd = scanner.next();
                    if (!oldPwd.equals(user.getPassword())) {
                        if (oldPwd.equals("E") || oldPwd.equals("e")){
                            System.out.println("返回用户界面");
                            menu.userMenu();
                            return;
                        }
                        else System.out.println("旧密码错误,请重试:");
                    }
                    else break;

                }
                System.out.println("请输入新密码（大于8位且包含大小写字母与特殊符号）:");
                String password;
                while (true) {
                    password = scanner.next();
                    if (!pwdClass.checkPassword(password)) {
                        System.out.println("密码要求大于8位且包含大小写字母与特殊符号,请重新输入:");
                    } else break;
                }
                user.setPassword(password);
                userController.modifyUser(userController.getUserString(user));  //更新数据库
                System.out.println("修改成功,请重新登录");
                menu.welcomeMenu();
                return;
            }
            case "2" -> {
                menu.shoppingMenu();
                return;
            }
            case "3" -> {
                menu.welcomeMenu();
                return;
            }
            default -> {
                System.out.println("您输入的操作有误，请重新选择");
            }
        }
        System.out.println();
        System.out.println("键入回车返回用户界面");
        menu.userMenu();
    }

    public void shoppingController(String choice) throws IOException {
        switch (choice){
            case"1"->{
                System.out.println();
                System.out.println("            用户界面>购物管理>添加购物车            ");
                System.out.println("***********************************************");
                System.out.println("在库商品:");
                System.out.println();
                goodsController.showAllGoods();
                System.out.println();
                System.out.println("请输入商品编号:");
                String id = scanner.next();
                if(goodsController.getGoodsById(id) == null){
                    System.out.println("商品不存在");
                }else {
                    System.out.println("商品:");
                    goodsController.showGoods(goodsController.getGoodsById(id));
                    System.out.println("请输入添加数量:");
                    String amount = scanner.next();
                    shoppingCartController.addToShoppingCart(shoppingCartController.getShoppingCartString(shoppingCartController.setShoppingCartGoods(id, amount)));
                    System.out.println("成功添加至购物车");
                }
            }
            case"2"->{
                System.out.println();
                System.out.println("            用户界面>购物管理>移除购物车            ");
                System.out.println("***********************************************");
                System.out.println("购物车列表:");
                System.out.println();
                shoppingCartController.showAllShoppingCartGoods();
                System.out.println();
                System.out.println("请输入要删除的商品id");
                shoppingCartController.deleteShoppingCart(scanner.next());

            }
            case "3"->{
                System.out.println();
                System.out.println("            用户界面>购物管理>修改购买数量            ");
                System.out.println("***********************************************");
                System.out.println("购物车列表:");
                System.out.println();
                shoppingCartController.showAllShoppingCartGoods();
                System.out.println();
                System.out.println("请输入商品编号:");
                String id = scanner.next();
                shoppingCartController.showShoppingCartGoods(shoppingCartController.getShoppingCartGoodsById(id));
                System.out.println();
                System.out.println("请输入修改后的商品数量:");
                String newAmount = scanner.next();
                shoppingCartController.modifyShoppingCartGoods(id, newAmount);
            }
            case"4"->{
                System.out.println();
                System.out.println("            用户界面>购物管理>购物车结算            ");
                System.out.println("***********************************************");
                shoppingCartController.showAllShoppingCartGoods();
                System.out.println();
                shoppingCartController.pay();
            }
            case"5"->{
                System.out.println();
                System.out.println("            用户界面>购物管理>查看购物历史            ");
                System.out.println("***********************************************");
                new ShoppingHistory().showHistory();
            }
            case"6"->{
                menu.userMenu();
                return;
            }
            default -> {
                System.out.println("您输入的操作有误，请重新选择");
            }
        }
        System.out.println();
        System.out.println("键入回车返回购物管理界面...");
        scanner.nextLine();
        scanner.nextLine();
        menu.shoppingMenu();
    }

}

