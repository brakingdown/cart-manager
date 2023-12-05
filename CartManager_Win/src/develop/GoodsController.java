package develop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GoodsController {
    public static String goodsFilePath = ".\\src\\develop\\Goods.csv";

    CsvManager goodsFile = new CsvManager(goodsFilePath);
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

    public Goods getGoodsById(String id) throws IOException{
        ArrayList<String[]> goodsList = goodsFile.read();
        Goods goods = null;
        for (int row = 1; row < goodsList.size(); row++) {
            if (id.equals(goodsList.get(row)[0])) {
                goods = setGoods(Integer.valueOf(goodsList.get(row)[0]),goodsList.get(row)[1], goodsList.get(row)[2],goodsList.get(row)[3], goodsList.get(row)[4],Float.valueOf(goodsList.get(row)[5]), Float.valueOf(goodsList.get(row)[6]),Integer.valueOf(goodsList.get(row)[7]));
            }

        }
        return goods;
    }

    public String[] getGoodsStringById(String id) throws IOException{
        ArrayList<String[]> goodsList = goodsFile.read();
        String[] goods = null;
        for (int row = 1; row < goodsList.size(); row++) {
            if (id.equals(goodsList.get(row)[0])) {
                goods = goodsList.get(row);
            }

        }
        return goods;
    }

    //返回一个商品对象的list
    public ArrayList<Goods> getAllGoods() throws IOException {
        ArrayList<String[]> goodsList = goodsFile.read();
        ArrayList<Goods> allGoods = new ArrayList<>();
        for (int row = 1; row < goodsList.size(); row++) {

            Goods goods = setGoods(Integer.valueOf(goodsList.get(row)[0]),goodsList.get(row)[1], goodsList.get(row)[2],goodsList.get(row)[3], goodsList.get(row)[4],Float.valueOf(goodsList.get(row)[5]), Float.valueOf(goodsList.get(row)[6]),Integer.valueOf(goodsList.get(row)[7]));

            allGoods.add(goods);

        }
        return allGoods;
    }

    //列出单个商品信息（带表头）
    public void showGoods(Goods goods) throws IOException {

        goodsFile.showHeader();
        System.out.println(goods.getId()+ " " + goods.getName() + " " + goods.getManufacturer() + " " + goods.getProduceDate() + " " + goods.getMarque() + " " + goods.getPurchasePrice() + " " + goods.getRetailPrice() + " " + goods.getAmount());
    }

    //列出商品信息（带表头）
    public void showAllGoods() throws IOException {
        ArrayList<Goods> allGoods = getAllGoods();

        goodsFile.showHeader();
        for(Goods goods : allGoods){
            System.out.println(goods.getId()+ " " + goods.getName() + " " + goods.getManufacturer() + " " + goods.getProduceDate() + " " + goods.getMarque() + " " + goods.getPurchasePrice() + " " + goods.getRetailPrice() + " " + goods.getAmount());
        }
    }

    //添加商品信息
    public void addGoods() throws IOException {
        ArrayList<String[]> goodsList = goodsFile.read();
        String[] goods = new String[8];
        int id = 0;
        for(int row = 1; row < goodsList.size(); row++){
            id = Integer.parseInt(goodsList.get(row)[0]);
        }
        id = id + 1;

        goods[0] = String.valueOf(id);
        System.out.println("请输入商品名称");
        goods[1] = scanner.nextLine();
        System.out.println("请输入生产厂家");
        goods[2] = scanner.nextLine();
        System.out.println("请输入生产日期");
        goods[3] = scanner.nextLine();
        System.out.println("请输入型号");
        goods[4] = scanner.nextLine();
        System.out.println("请输入进货价");
        goods[5] = scanner.nextLine();
        System.out.println("请输入零售价");
        goods[6] = scanner.nextLine();
        System.out.println("请输入数量");
        goods[7] = scanner.nextLine();

        goodsList.add(goods);
        goodsFile.rewrite(goodsList);
        System.out.println("添加成功");
    }

    //修改商品信息
    public int modifyGoods() throws IOException {

        System.out.println("请输入商品ID");
        String id = scanner.next();
        System.out.println("请输入进货价");
        String purchasePrice = scanner.next();
        System.out.println("请输入零售价");
        String retailPrice = scanner.next();
        System.out.println("请输入数量");
        String amount = scanner.next();

        ArrayList<String[]> goodsList = goodsFile.read();
        for(int row = 1; row < goodsList.size(); row++){
            if(id.equals(goodsList.get(row)[0])){
                String[] modifiedGoods = new String[]{id, goodsList.get(row)[1], goodsList.get(row)[2], goodsList.get(row)[3], goodsList.get(row)[4], purchasePrice, retailPrice, amount};
                goodsList.set(row, modifiedGoods);
                goodsFile.rewrite(goodsList);

                return 1;
            }
        }
        return 0;
    }
    //更新商品
    public void updateGoods(String[] modifiedGoods) throws IOException {
        ArrayList<String[]> goodsList = goodsFile.read();
        for(int row = 1; row < goodsList.size(); row++){
            if(modifiedGoods[0].equals(goodsList.get(row)[0])){
                goodsList.set(row,modifiedGoods);
                goodsFile.rewrite(goodsList);
            }
        }
    }

    //删除商品
    public void deleteGoods(String id) throws IOException {
        ArrayList<String[]> goodsList = goodsFile.read();

        for(int row = 1; row < goodsList.size(); row++){
            if(id.equals(goodsList.get(row)[0])){
                System.out.println("请确认是否删除以下商品(y/n)");
                showGoods(getGoodsById(id));
                String judge = scanner.next();
                if(judge.equals("y") || judge.equals("Y")){
                    goodsList.remove(row);
                    goodsFile.rewrite(goodsList);
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

}
