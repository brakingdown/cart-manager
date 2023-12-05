package develop;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ShoppingCartController {
    DatabaseManager dbManager = new DatabaseManager();
    String getAll = "SELECT * FROM ShoppingCart";
    String table = "ShoppingCart";
    GoodsController goodsController = new GoodsController();
    Scanner scanner = new Scanner(System.in);
    ShoppingHistory history = new ShoppingHistory();

    public ShoppingCart setShoppingCartGoods(String id, String amount) throws SQLException {
        ShoppingCart shoppingCart = new ShoppingCart();
        Goods goods = goodsController.getGoodsById(String.valueOf(id));

        shoppingCart.setId(Integer.valueOf(id));
        shoppingCart.setName(goods.getName());
        shoppingCart.setRetailPrice(goods.getRetailPrice());
        shoppingCart.setAmount(Integer.valueOf(amount));

        return shoppingCart;
    }

    public String[] getShoppingCartString(ShoppingCart shoppingCart) {
        String[] shoppingCartString = new String[4];

        shoppingCartString[0] = String.valueOf(shoppingCart.getId());
        shoppingCartString[1] = shoppingCart.getName();
        shoppingCartString[2] = String.valueOf(shoppingCart.getRetailPrice());
        shoppingCartString[3] = String.valueOf(shoppingCart.getAmount());

        return shoppingCartString;
    }


    public void addToShoppingCart(String[] shoppingCartGoodsString) throws SQLException {
        long 商品编号 = Long.parseLong(shoppingCartGoodsString[0]);
        String 商品名称 = shoppingCartGoodsString[1];
        BigDecimal 商品零售价 = new BigDecimal(shoppingCartGoodsString[2]);
        long 购买数量 = Long.parseLong(shoppingCartGoodsString[3]);

        ArrayList<String[]> shoppingCartList = dbManager.read(getAll);

        if (!shoppingCartList.isEmpty()) {
            // 商品已存在于购物车，更新数量
            String[] existingItem = shoppingCartList.get(0);
            long existingAmount = Long.parseLong(existingItem[3]);
            long newAmount = existingAmount + 购买数量;

            dbManager.updateShoppingCart(商品编号, 商品名称, 商品零售价, newAmount); // 假设这个方法可以更新购物车中商品的数量
        } else {
            // 商品不存在于购物车，添加新商品
            dbManager.insertShoppingCart(商品编号, 商品名称, 商品零售价, 购买数量);
        }
    }


    public ShoppingCart getShoppingCartGoodsById(String id) throws SQLException {
        String sql = "SELECT * FROM ShoppingCart WHERE 商品编号 = ?";
        ArrayList<String[]> shoppingCartList = dbManager.read(sql, id);
        ShoppingCart shoppingCartGoods = null;
        if (!shoppingCartList.isEmpty()) {
            String[] row = shoppingCartList.get(0);
            shoppingCartGoods = setShoppingCartGoods(row[0], row[3]);
        }
        return shoppingCartGoods;
    }


    public ArrayList<ShoppingCart> getFullShoppingCart() throws SQLException {
        ArrayList<String[]> shoppingCartList = dbManager.read(getAll);
        ArrayList<ShoppingCart> fullShoppingCart = new ArrayList<>();
        for (String[] strings : shoppingCartList) {

            ShoppingCart goods = setShoppingCartGoods(strings[0], strings[3]);

            fullShoppingCart.add(goods);

        }
        return fullShoppingCart;
    }

    public void showShoppingCartGoods(ShoppingCart shoppingCartGoods) throws SQLException {

        dbManager.showHeader(table);
        System.out.println(shoppingCartGoods.getId()+" "+ shoppingCartGoods.getName()+" "+shoppingCartGoods.getRetailPrice()+" "+shoppingCartGoods.getAmount());
    }

    public void showAllShoppingCartGoods() throws SQLException {
        ArrayList<ShoppingCart> shoppingCart = getFullShoppingCart();

        dbManager.showHeader(table);
        for(ShoppingCart goods : shoppingCart){
            System.out.println(goods.getId()+" "+ goods.getName()+" "+goods.getRetailPrice()+" "+goods.getAmount());

        }

    }

    public void deleteShoppingCart(String id) throws SQLException {
        long 商品编号 = Long.parseLong(id);

        System.out.println("请确认是否在购物车中删除以下商品(y/n)");
        ShoppingCart shoppingCartGoods = getShoppingCartGoodsById(id); // 获取购物车中的商品信息
        if(shoppingCartGoods != null) {
            showShoppingCartGoods(shoppingCartGoods); // 显示商品信息
            String judge = scanner.next();
            if(judge.equalsIgnoreCase("y")){
                dbManager.deleteShoppingCart(商品编号); // 调用 deleteShoppingCart 方法进行删除
                System.out.println("删除成功");
            } else if(judge.equalsIgnoreCase("n")){
                System.out.println("取消删除");
            } else {
                System.out.println("输入错误");
            }
        } else {
            System.out.println("商品不存在");
        }
    }

    //修改商品数量
    public void modifyShoppingCartGoods(String id, String newAmount) throws SQLException {
        long 商品编号 = Long.parseLong(id);
        int 购买数量 = Integer.parseInt(newAmount);

        if(购买数量 <= 0){
            // 如果新的购买数量小于或等于0，则删除该商品
            dbManager.deleteShoppingCart(商品编号);
            System.out.println("商品已从购物车中删除");
        } else {
            // 从购物车获取商品的其他信息
            ShoppingCart shoppingCartGoods = getShoppingCartGoodsById(id); // 假设这个方法能够根据ID获取购物车中的商品信息
            if (shoppingCartGoods != null) {
                // 更新购物车中的商品数量
                dbManager.updateShoppingCart(商品编号, shoppingCartGoods.getName(), BigDecimal.valueOf(shoppingCartGoods.getRetailPrice()), 购买数量);
                System.out.println("购物车商品数量已修改");
            } else {
                System.out.println("商品不存在");
            }
        }
    }


    public void pay() throws SQLException{
        float price = 0f;
        ArrayList<String[]> shoppingCartList = dbManager.read(getAll);
        String[] headers = dbManager.getHeaders(table);

        for (String[] strings : shoppingCartList) {
            float money = Float.parseFloat(strings[2]) * Integer.parseInt(strings[3]);
            price += money;
        }

        System.out.println("总金额为: " + price);
        System.out.println();
        System.out.println("输入 Y 确认结账");
        String judge = scanner.next();
        if(judge.equals("Y") || judge.equals("y")){
            System.out.println("结账成功");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String purchaseDate = df.format(new Date());

            //写历史记录
            history.writeHistory(shoppingCartList, purchaseDate);

            //修改商品数量
            for(int row = 1; row < shoppingCartList.size(); row++) {
                String id = shoppingCartList.get(row)[0];
                String[] modifiedGoods = goodsController.getGoodsStringById(id);
                int newAmount = Integer.parseInt(modifiedGoods[7]) - Integer.parseInt(shoppingCartList.get(row)[3]);
                modifiedGoods[7] = String.valueOf(newAmount);
                goodsController.updateGoods(modifiedGoods);
            }

            // 清空购物车
            dbManager.clearShoppingCart();
        }
        else{
            System.out.println("退出结账");
        }

    }
}
