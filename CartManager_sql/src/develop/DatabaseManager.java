package develop;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/Shopping?user=root"; //Personalised
    private static final String USER = "root"; //Personalised
    private static final String PASSWORD = "PaSpSAps5"; //Personalised

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public ArrayList<String[]> read(String query) throws SQLException {
        ArrayList<String[]> results = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                results.add(row);
            }
        }
        return results;
    }

    public ArrayList<String[]> read(String query, String... params) throws SQLException {
        ArrayList<String[]> results = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // 设置预处理语句的参数
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }

            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                results.add(row);
            }
        }
        return results;
    }

    // Admin 表的写入方法
    public void insertAdmin(String adminName, String adminPassword) throws SQLException {
        String sql = "INSERT INTO Admin (管理员用户名, 管理员密码) VALUES (?, ?);";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, adminName);
            pstmt.setString(2, adminPassword);

            pstmt.executeUpdate();
        }
    }

    // Admin 表的更新方法
    public void updateAdmin(String adminName, String newPassword) throws SQLException {
        String sql = "UPDATE Admin SET 管理员密码 = ? WHERE 管理员用户名 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, adminName);

            pstmt.executeUpdate();
        }
    }

    // User 表的写入方法
    public void insertUser(long id, String username, String vipLevel, String signDate, BigDecimal totalMoney, String phoneNumber, String email, String password) throws SQLException {
        String sql = "INSERT INTO User (客户ID, 用户名, 用户级别, 用户注册时间, 客户累计消费总金额, 用户手机号, 用户邮箱, 用户密码) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, vipLevel);
            pstmt.setString(4, signDate); // 注意：这里是字符串
            pstmt.setBigDecimal(5, totalMoney);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, email);
            pstmt.setString(8, password);

            pstmt.executeUpdate();
        }
    }


    public boolean updateUserPasswordByPhone(String phoneNum, String newPassword) throws SQLException {
        String sql = "UPDATE User SET 用户密码 = ? WHERE 用户手机号 = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, phoneNum);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean updateUserPasswordByID(Long id, String newPassword) throws SQLException {
        String sql = "UPDATE User SET 用户密码 = ? WHERE 客户ID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPassword);
            pstmt.setLong(2, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // 如果影响的行数大于0，则返回true，表示更新成功
        }
    }

    public void updateUserTotalMoneyByID(Long id, BigDecimal totalMoney) throws SQLException {
        String sql = "UPDATE User SET 客户累计消费总金额 = ? WHERE 客户ID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBigDecimal(1, totalMoney);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        }
    }

    // User 表的删除方法
    public void deleteUserByID(long id) throws SQLException {
        String sql = "DELETE FROM User WHERE 客户ID = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteUserByPhoneNum(String phoneNumber) throws SQLException {
        String sql = "DELETE FROM User WHERE 用户手机号 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, phoneNumber);
            pstmt.executeUpdate();
        }
    }

    // Goods 表的写入方法
    public void insertGoods(long id, String name, String manufacturer, String produceDate,
                            String marque, BigDecimal purchasePrice, BigDecimal retailPrice, long amount) throws SQLException {
        String sql = "INSERT INTO Goods (商品编号, 商品名称, 生产厂家, 生产日期, 型号, 进货价, 零售价, 数量) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, manufacturer);
            pstmt.setString(4, produceDate); // 注意：这里是字符串
            pstmt.setString(5, marque);
            pstmt.setBigDecimal(6, purchasePrice);
            pstmt.setBigDecimal(7, retailPrice);
            pstmt.setLong(8, amount);

            pstmt.executeUpdate();
        }
    }

    // Goods 表的更新方法
    public void updateGoods(long id, String name, String manufacturer, String produceDate,
                            String marque, BigDecimal purchasePrice, BigDecimal retailPrice, long amount) throws SQLException {
        String sql = "UPDATE Goods SET 商品名称 = ?, 生产厂家 = ?, 生产日期 = ?, 型号 = ?, 进货价 = ?, 零售价 = ?, 数量 = ? WHERE 商品编号 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, manufacturer);
            pstmt.setString(3, produceDate);
            pstmt.setString(4, marque);
            pstmt.setBigDecimal(5, purchasePrice);
            pstmt.setBigDecimal(6, retailPrice);
            pstmt.setLong(7, amount);
            pstmt.setLong(8, id);

            pstmt.executeUpdate();
        }
    }

    // Goods 表的删除方法
    public void deleteGoods(long id) throws SQLException {
        String sql = "DELETE FROM Goods WHERE 商品编号 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    // ShoppingCart 表的写入方法
    public void insertShoppingCart(long id, String name, BigDecimal retailPrice, long amount) throws SQLException {
        String sql = "INSERT INTO ShoppingCart (商品编号, 商品名称, 商品零售价, 购买数量) VALUES (?, ?, ?, ?);";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.setString(2, name);
            pstmt.setBigDecimal(3, retailPrice);
            pstmt.setLong(4, amount);

            pstmt.executeUpdate();
        }
    }

    // ShoppingCart 表的更新方法
    public void updateShoppingCart(long id, String name, BigDecimal retailPrice, long amount) throws SQLException {
        String sql = "UPDATE ShoppingCart SET 商品名称 = ?, 商品零售价 = ?, 购买数量 = ? WHERE 商品编号 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setBigDecimal(2, retailPrice);
            pstmt.setLong(3, amount);
            pstmt.setLong(4, id);

            pstmt.executeUpdate();
        }
    }

    // ShoppingCart 表的删除方法
    public void deleteShoppingCart(long id) throws SQLException {
        String sql = "DELETE FROM ShoppingCart WHERE 商品编号 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    public void clearShoppingCart() throws SQLException {
        String sql = "DELETE FROM ShoppingCart";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }
    }


    // ShoppingHistory 表的写入方法
    public void insertShoppingHistory(String purchaseTime, String name) throws SQLException {
        String sql = "INSERT INTO ShoppingHistory (购买时间, 商品名称) VALUES (?, ?);";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, purchaseTime);
            pstmt.setString(2, name);

            pstmt.executeUpdate();
        }
    }

    // 获取表头的通用方法
    public String[] getHeaders(String table) throws SQLException {
        String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'Shopping' AND TABLE_NAME = ? ORDER BY ORDINAL_POSITION"; //Personalised
        ArrayList<String> headers = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, table);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                headers.add(rs.getString("COLUMN_NAME"));
            }
        }
        return headers.toArray(new String[0]);
    }

    // 显示表头的通用方法
    public void showHeader(String table) throws SQLException {
        String[] headers = getHeaders(table);
        for (String header : headers) {
            System.out.print(header + "  ");
        }
        System.out.println();

    }

}


