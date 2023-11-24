package develop;

import java.io.IOException;
import java.util.ArrayList;

import static develop.AdminController.adminFilePath;
import static develop.GoodsController.goodsFilePath;
import static develop.ShoppingCartController.shoppingCartFilePath;
import static develop.ShoppingHistory.historyFilePath;
import static develop.UserController.userFilePath;

public class CreatNewFile {

    public static String[] adminHeaders = new String[]{"\ufeff管理员用户名", "管理员密码"};
    public static String[] originalAdmin = new String[]{"admin", "ynuadmin"};

    public static String[] userHeaders = new String[]{"\ufeff客户ID","用户名","用户级别","用户注册时间","客户累计消费总金额","用户手机号","用户邮箱","用户密码"};
    public static String[] originalUser = new String[]{"0","username", "1", "2018/12/19", "2000.0", "13852471292","189189851@qq.com","12345678"};

    public static String[] goodsHeaders = new String[]{"\ufeff商品编号", "商品名称", "生产厂家", "生产日期", "型号", "进货价", "零售价", "数量"};
    public static String[] originalGoods = new String[]{"0", "iPhone", "Apple", "2014/9/8", "iPhone12", "5000.0", "6999.0", "10"};

    public static String[] shoppingCartHeaders = new String[]{"\ufeff商品编号", "商品名称", "商品零售价", "购买数量"};

    public static String[] historyHeaders = new String[]{"\ufeff购买时间", "商品"};


    public void creatFiles()throws IOException {
        creatFile(adminFilePath, adminHeaders, originalAdmin);
        creatFile(userFilePath, userHeaders, originalUser);
        creatFile(goodsFilePath, goodsHeaders, originalGoods);
        creatFile(shoppingCartFilePath, shoppingCartHeaders);
        creatFile(historyFilePath, historyHeaders);
        System.out.println("所有文件创建完毕");
    }

    public void creatFile(String filePath, String[] headers, String[] record) throws IOException {
        CsvManager file = new CsvManager(filePath);
        ArrayList<String[]> list = new ArrayList<>();
        list.add(headers);
        list.add(record);
        file.rewrite(list);
    }

    public void creatFile(String filePath, String[] headers) throws IOException {
        CsvManager file = new CsvManager(filePath);
        ArrayList<String[]> list = new ArrayList<>();
        list.add(headers);
        file.rewrite(list);
    }
}
