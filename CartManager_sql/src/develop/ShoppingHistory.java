package develop;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShoppingHistory {

    DatabaseManager dbManager = new DatabaseManager();
    String getAll = "SELECT * FROM ShoppingHistory";
    public void showHistory() throws SQLException {
        ArrayList<String[]> historyList = dbManager.read(getAll);
        for (String[] record : historyList){
            System.out.println(record[0] + " " + record[1]);
        }
    }

    public void writeHistory(ArrayList<String[]> shoppingCartList, String purchaseDate) throws SQLException {
        for (String[] item : shoppingCartList) {
            String goodsName = item[1];
            dbManager.insertShoppingHistory(purchaseDate, goodsName);
        }
    }

}
