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
        long gid = Long.parseLong(shoppingCartGoodsString[0]);
        BigDecimal gRetailPrice = new BigDecimal(shoppingCartGoodsString[2]);
        long gAmount = Long.parseLong(shoppingCartGoodsString[3]);

        // 查询指定商品的库存量
        String goodsSql = "SELECT 数量 FROM Goods WHERE 商品编号 = ?";
        ArrayList<String[]> goodsList = dbManager.read(goodsSql, String.valueOf(gid));
        long gStock = 0;
        if (!goodsList.isEmpty()) {
            gStock = Long.parseLong(goodsList.get(0)[0]);
        }

        // 检查购物车中是否已有该商品
        String cartSql = "SELECT * FROM ShoppingCart WHERE 商品编号 = ?";
        ArrayList<String[]> shoppingCartList = dbManager.read(cartSql, String.valueOf(gid));

        long existingAmount = shoppingCartList.isEmpty() ? 0 : Long.parseLong(shoppingCartList.get(0)[3]);
        long totalAmount = existingAmount + gAmount;

        // 比较购买数量与商品库存
        if (totalAmount > gStock) {
            System.out.println("添加失败：购买数量超过商品库存！");
        } else {
            if (!shoppingCartList.isEmpty()) {
                // 商品已存在于购物车，更新数量
                dbManager.updateShoppingCart(gid, shoppingCartGoodsString[1], gRetailPrice, totalAmount);
            } else {
                // 商品不存在于购物车，添加新商品
                dbManager.insertShoppingCart(gid, shoppingCartGoodsString[1], gRetailPrice, gAmount);
            }
            System.out.println("商品已成功添加到购物车");
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
        long gid = Long.parseLong(id);

        ShoppingCart shoppingCartGoods = getShoppingCartGoodsById(id); // 获取购物车中的商品信息
        if(shoppingCartGoods != null) {
            System.out.println("请确认是否在购物车中删除以下商品(y/n)");
            showShoppingCartGoods(shoppingCartGoods); // 显示商品信息
            String judge = scanner.next();
            if(judge.equalsIgnoreCase("y")){
                dbManager.deleteShoppingCart(gid); // 调用 deleteShoppingCart 方法进行删除
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
        long gid = Long.parseLong(id);
        int gAmount = Integer.parseInt(newAmount);

        if(gAmount <= 0){
            // 如果新的购买数量小于或等于0，则删除该商品
            dbManager.deleteShoppingCart(gid);
            System.out.println("商品已从购物车中删除");
        } else {
            // 从购物车获取商品的其他信息
            ShoppingCart shoppingCartGoods = getShoppingCartGoodsById(id);
            if (shoppingCartGoods != null) {
                // 更新购物车中的商品数量
                dbManager.updateShoppingCart(gid, shoppingCartGoods.getName(), BigDecimal.valueOf(shoppingCartGoods.getRetailPrice()), gAmount);
                System.out.println("购物车商品数量已修改");
            } else {
                System.out.println("商品不存在");
            }
        }
    }


    public void pay(User user) throws SQLException {
        float price = 0f;
        ArrayList<String[]> shoppingCartList = dbManager.read(getAll);

        for (String[] shoppingCartItem : shoppingCartList) {
            long gid = Long.parseLong(shoppingCartItem[0]);
            long gAmount = Long.parseLong(shoppingCartItem[3]);
            float money = Float.parseFloat(shoppingCartItem[2]) * gAmount;
            price += money;

            // 获取当前商品的完整信息
            String[] modifiedGoods = goodsController.getGoodsStringById(String.valueOf(gid));
            if (modifiedGoods != null) {
                long newStock = Long.parseLong(modifiedGoods[7]) - gAmount;

                // 如果库存为0，则删除商品，否则更新库存
                if (newStock <= 0) {
                    dbManager.deleteGoods(gid);
                } else {
                    // 调用 dbManager.updateGoods 更新库存
                    dbManager.updateGoods(gid, modifiedGoods[1], modifiedGoods[2], modifiedGoods[3], modifiedGoods[4], new BigDecimal(modifiedGoods[5]), new BigDecimal(modifiedGoods[6]), newStock);
                }
            }
        }

        System.out.println("总金额为: " + price);
        System.out.println();
        System.out.println("输入 Y 确认结账");
        String judge = scanner.next();
        if (judge.equalsIgnoreCase("y")) {
            System.out.println("结账成功");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String purchaseDate = df.format(new Date());

            // 写历史记录
            history.writeHistory(shoppingCartList, purchaseDate);

            // 更新用户的累计消费总金额
            BigDecimal currentTotal = BigDecimal.valueOf(user.getMoney());
            BigDecimal newTotal = currentTotal.add(BigDecimal.valueOf(price));
            dbManager.updateUserTotalMoneyByID(Long.valueOf(user.getId()), newTotal);

            // 清空购物车
            dbManager.clearShoppingCart();

            System.out.println("用户累计消费总金额已更新");
        } else {
            System.out.println("退出结账");
        }
    }


}
