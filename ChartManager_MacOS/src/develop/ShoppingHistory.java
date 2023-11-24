package develop;

import java.io.IOException;
import java.util.ArrayList;

public class ShoppingHistory {
    public static String historyFilePath = "./src/develop/ShoppingHistory.csv";

    CsvManager historyFile = new CsvManager(historyFilePath);

    public void showHistory() throws IOException {
        ArrayList<String[]> historyList = historyFile.read();
        for (String[] record : historyList){
            System.out.println(record[0] + " " + record[1]);
        }
    }

    public void writeHistory(ArrayList<String[]> shoppingCartList, String purchaseDate) throws IOException {
        ArrayList<String[]> historyList = historyFile.read();
        for(int row = 1; row < shoppingCartList.size(); row++){
            String[] record = new String[]{purchaseDate, shoppingCartList.get(row)[1]};
            historyList.add(record);
        }
        historyFile.rewrite(historyList);
    }

}
