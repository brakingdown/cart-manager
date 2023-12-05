package develop;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ShoppingCartController {
    public static String shoppingCartFilePath = "./src/develop/ShoppingCart.csv";

    GoodsController goodsController = new GoodsController();
    CsvManager shoppingCartFile = new CsvManager(shoppingCartFilePath);
    Scanner scanner = new Scanner(System.in);
    ShoppingHistory history = new ShoppingHistory();

    public ShoppingCart setShoppingCartGoods(String id, String amount) throws IOException {
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


    public void addToShoppingCart(String[] shoppingCartGoodsString) throws IOException {
        ArrayList<String[]> shoppingCartList = shoppingCartFile.read();
        for(int row = 1; row < shoppingCartList.size(); row++){
            if(shoppingCartGoodsString[0].equals(shoppingCartList.get(row)[0])){
                Integer newAmount = Integer.parseInt(shoppingCartList.get(row)[3]) + Integer.parseInt(shoppingCartGoodsString[3]);

                shoppingCartList.set(row,getShoppingCartString(setShoppingCartGoods(shoppingCartGoodsString[0], String.valueOf(newAmount))));
                shoppingCartFile.rewrite(shoppingCartList);
                return;
            }
        }
        shoppingCartList.add(shoppingCartGoodsString);
        shoppingCartFile.rewrite(shoppingCartList);

    }

    public ShoppingCart getShoppingCartGoodsById(String id) throws IOException{
        ArrayList<String[]> shoppingCartList = shoppingCartFile.read();
        ShoppingCart shoppingCartGoods = null;
        for (int row = 1; row < shoppingCartList.size(); row++) {
            if (id.equals(shoppingCartList.get(row)[0])) {
                shoppingCartGoods = setShoppingCartGoods(shoppingCartList.get(row)[0],shoppingCartList.get(row)[3]);
            }

        }
        return shoppingCartGoods;
    }

    public ArrayList<ShoppingCart> getFullShoppingCart() throws IOException {
        ArrayList<String[]> shoppingCartList = shoppingCartFile.read();
        ArrayList<ShoppingCart> fullShoppingCart = new ArrayList<>();
        for (int row = 1; row < shoppingCartList.size(); row++) {

            ShoppingCart goods = setShoppingCartGoods(shoppingCartList.get(row)[0], shoppingCartList.get(row)[3]);

            fullShoppingCart.add(goods);

        }
        return fullShoppingCart;
    }

    public void showShoppingCartGoods(ShoppingCart shoppingCartGoods) throws IOException {

        shoppingCartFile.showHeader();
        System.out.println(shoppingCartGoods.getId()+" "+ shoppingCartGoods.getName()+" "+shoppingCartGoods.getRetailPrice()+" "+shoppingCartGoods.getAmount());
    }

    public void showAllShoppingCartGoods() throws IOException {
        ArrayList<ShoppingCart> shoppingCart = getFullShoppingCart();

        shoppingCartFile.showHeader();
        for(ShoppingCart goods : shoppingCart){
            System.out.println(goods.getId()+" "+ goods.getName()+" "+goods.getRetailPrice()+" "+goods.getAmount());

        }

    }

    public void deleteShoppingCart(String id) throws IOException {
        ArrayList<String[]> shoppingCartList = shoppingCartFile.read();
        for(int row = 1; row < shoppingCartList.size(); row++){
            if(id.equals(shoppingCartList.get(row)[0])){
                System.out.println("请确认是否在购物车中删除以下商品(y/n)");
                showShoppingCartGoods(getShoppingCartGoodsById(id));
                String judge = scanner.next();
                if(judge.equals("y") || judge.equals("Y")){
                    shoppingCartList.remove(row);
                    shoppingCartFile.rewrite(shoppingCartList);
                    System.out.println("删除成功");
                    return;
                } else if(judge.equals("n") || judge.equals("N")){
                    System.out.println("取消删除");
                    return;
                } else{
                    System.out.println("输入错误");
                    return;
                }

            }
        }
        System.out.println("商品不存在");
    }

    //修改商品数量
    public void modifyShoppingCartGoods(String id, String newAmount) throws IOException {
        ArrayList<String[]> shoppingCartList = shoppingCartFile.read();

        for(int row = 1; row < shoppingCartList.size(); row++){
            if(id.equals(shoppingCartList.get(row)[0])){
                if(Integer.parseInt(newAmount) <= 0){
                    shoppingCartList.remove(row);
                }else {
                    shoppingCartList.set(row, getShoppingCartString(setShoppingCartGoods(id, newAmount)));
                }
                shoppingCartFile.rewrite(shoppingCartList);
                System.out.println("修改成功");
                return;
            }

        }
        System.out.println("商品不存在");
    }

    public void pay() throws IOException {
        float price = 0f;
        ArrayList<String[]> shoppingCartList = shoppingCartFile.read();
        String[] headers = shoppingCartFile.getHeaders();

        for(int row = 1; row < shoppingCartList.size(); row++) {
            float money = Float.parseFloat(shoppingCartList.get(row)[2]) * Integer.parseInt(shoppingCartList.get(row)[3]);
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
            ArrayList<String[]> blank = new ArrayList<>();
            blank.add(headers);
            shoppingCartFile.rewrite(blank);
        }
        else{
            System.out.println("退出结账");
        }

    }
}
