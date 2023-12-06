package develop;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class GoodsController {
    DatabaseManager dbManager = new DatabaseManager();
    String getAll = "SELECT * FROM Goods";
    String table = "Goods";
    Scanner scanner = new Scanner(System.in);

    public Goods setGoods(Integer id, String name, String manufacturer, String produceDate, String marque, Float purchasePrice, Float retailPrice, Integer amount){
        Goods goods = new Goods();
        goods.setId(id);
        goods.setName(name);
        goods.setManufacturer(manufacturer);
        goods.setProduceDate(produceDate);
        goods.setMarque(marque);
        goods.setPurchasePrice(purchasePrice);
        goods.setRetailPrice(retailPrice);
        goods.setAmount(amount);
        return goods;
    }

    public Goods getGoodsById(String id) throws SQLException{
        String sql = "SELECT * FROM Goods WHERE 商品编号 = ?";
        ArrayList<String[]> goodsList = dbManager.read(sql, id);
        Goods goods = null;
        if (!goodsList.isEmpty()) {
            String[] row = goodsList.get(0);
            goods = setGoods(Integer.valueOf(row[0]), row[1], row[2], row[3], row[4], Float.valueOf(row[5]), Float.valueOf(row[6]), Integer.valueOf(row[7]));
        }
        return goods;
    }


    public String[] getGoodsStringById(String id) throws SQLException{
        String sql = "SELECT * FROM Goods WHERE 商品编号 = ?";
        ArrayList<String[]> goodsList = dbManager.read(sql, id);
        if (!goodsList.isEmpty()) {
            return goodsList.get(0);
        }
        return null;
    }


    //返回一个商品对象的list
    public ArrayList<Goods> getAllGoods() throws SQLException {
        ArrayList<String[]> goodsList = dbManager.read(getAll);
        ArrayList<Goods> allGoods = new ArrayList<>();
        for (String[] strings : goodsList) {

            Goods goods = setGoods(Integer.valueOf(strings[0]), strings[1], strings[2], strings[3], strings[4], Float.valueOf(strings[5]), Float.valueOf(strings[6]), Integer.valueOf(strings[7]));

            allGoods.add(goods);

        }
        return allGoods;
    }

    //列出单个商品信息（带表头）
    public void showGoods(Goods goods) throws SQLException {

        dbManager.showHeader(table);
        System.out.println(goods.getId()+ " " + goods.getName() + " " + goods.getManufacturer() + " " + goods.getProduceDate() + " " + goods.getMarque() + " " + goods.getPurchasePrice() + " " + goods.getRetailPrice() + " " + goods.getAmount());
    }

    //列出商品信息（带表头）
    public void showAllGoods() throws SQLException {
        ArrayList<Goods> allGoods = getAllGoods();

        dbManager.showHeader(table);
        for(Goods goods : allGoods){
            System.out.println(goods.getId()+ " " + goods.getName() + " " + goods.getManufacturer() + " " + goods.getProduceDate() + " " + goods.getMarque() + " " + goods.getPurchasePrice() + " " + goods.getRetailPrice() + " " + goods.getAmount());
        }
    }

    //添加商品信息
    public void addGoods() throws SQLException {
        ArrayList<String[]> goodsList = dbManager.read(getAll);
        String[] goods = new String[8];
        int id = 0;
        for (String[] strings : goodsList) {
            id = Integer.parseInt(strings[0]);
        }
        id = id + 1;

        goods[0] = String.valueOf(id);

        goods[1] = getInput("请输入商品名称");
        goods[2] = getInput("请输入生产厂家");
        goods[3] = getInput("请输入生产日期");
        goods[4] = getInput("请输入型号");
        goods[5] = getInput("请输入进货价");
        goods[6] = getInput("请输入零售价");
        goods[7] = getInput("请输入数量");

        long gid = Long.parseLong(goods[0]);
        String gName = goods[1];
        String gManufacturer = goods[2];
        String gProduceDate = goods[3];
        String gMarque = goods[4];
        BigDecimal gPurchasePrice = new BigDecimal(goods[5]);
        BigDecimal gRetailPrice = new BigDecimal(goods[6]);
        long gAmount = Long.parseLong(goods[7]);

        dbManager.insertGoods(gid, gName, gManufacturer, gProduceDate, gMarque, gPurchasePrice, gRetailPrice, gAmount);
        System.out.println("添加成功");
    }

    private String getInput(String prompt) {
        String input;
        do {
            System.out.println(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("输入不能为空，请重新输入！");
            }
        } while (input.isEmpty());
        return input;
    }

    //修改商品信息
    public int modifyGoods() throws SQLException {

        System.out.println("请输入商品ID");
        long gid = Long.parseLong(scanner.next());
        System.out.println("请输入进货价");
        BigDecimal gPurchasePrice = new BigDecimal(scanner.next());
        System.out.println("请输入零售价");
        BigDecimal gRetailPrice = new BigDecimal(scanner.next());
        System.out.println("请输入数量");
        long gAmount = Long.parseLong(scanner.next());

        String sql = "SELECT * FROM Goods WHERE 商品编号 = ?";
        ArrayList<String[]> goodsList = dbManager.read(sql, String.valueOf(gid));

        if (!goodsList.isEmpty()) {
            String[] goodsInfo = goodsList.get(0); // 获取当前商品信息
            // 使用用户输入的新信息更新商品信息
            dbManager.updateGoods(gid, goodsInfo[1], goodsInfo[2], goodsInfo[3], goodsInfo[4], gPurchasePrice, gRetailPrice, gAmount);
            return 1; // 表示更新成功
        }

        return 0; // 商品ID不存在，更新失败
    }

    //删除商品
    public void deleteGoods(String id) throws SQLException {
        long gid = Long.parseLong(id);

        System.out.println("请确认是否删除以下商品(y/n)");
        showGoods(getGoodsById(id)); // 假设 getGoodsById 方法能够根据商品编号返回相应的商品信息
        String judge = scanner.next();
        if(judge.equalsIgnoreCase("y")){
            dbManager.deleteGoods(gid); // 直接调用 deleteGoods 方法进行删除
            System.out.println("删除成功");
        } else if(judge.equalsIgnoreCase("n")){
            System.out.println("取消删除");
        } else{
            System.out.println("输入错误");
        }
    }


}
