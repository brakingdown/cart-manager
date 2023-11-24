package develop;

import com.csvreader.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CsvManager {
    private String csvFilePath;
    public CsvManager(String csvFilePath){
        this.csvFilePath = csvFilePath;
    }
    public ArrayList<String[]> read() throws IOException {

        try {
            CsvReader csvReader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
            ArrayList<String[]> csvFileList = new ArrayList<>();

            //csvReader.readHeaders();//读取表头

            while (csvReader.readRecord()) {
                csvFileList.add(csvReader.getValues());
            }

            csvReader.close();
            return csvFileList;

        }catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public void write(String[] content) throws IOException {
        FileOutputStream out = new FileOutputStream(csvFilePath, true);
        CsvWriter csvWriter = new CsvWriter(out, ',', Charset.forName("UTF-8"));

        csvWriter.writeRecord(content);
        csvWriter.close();
    }

    public void rewrite(ArrayList<String[]> list) throws IOException {
        CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("UTF-8"));

        for(String[] row: list) {
            csvWriter.writeRecord(row);
        }
        csvWriter.close();
    }

    public String[] getHeaders() throws IOException {
        CsvReader csvReader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
        String[] headers = null;
        if(csvReader.readHeaders()) {
            headers = csvReader.getHeaders();
        }
        csvReader.close();
        return headers;
    }

    public void showHeader() throws IOException {
        String[] headers = getHeaders();
        if (headers != null) {
            for (String label : headers) {
                System.out.print(label + "  ");
            }
            System.out.println();
        }
        else System.out.println("无表头");
    }
}
