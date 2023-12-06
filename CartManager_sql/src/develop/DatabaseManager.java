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
    public void insertAdmin(String 管理员用户名, String 管理员密码) throws SQLException {
        String sql = "INSERT INTO Admin (管理员用户名, 管理员密码) VALUES (?, ?);";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, 管理员用户名);
            pstmt.setString(2, 管理员密码);

            pstmt.executeUpdate();
        }
    }

    // Admin 表的更新方法
    public void updateAdmin(String 管理员用户名, String 新密码) throws SQLException {
        String sql = "UPDATE Admin SET 管理员密码 = ? WHERE 管理员用户名 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, 新密码);
            pstmt.setString(2, 管理员用户名);

            pstmt.executeUpdate();
        }
    }

    public void updateUserPasswordByPhone(String phoneNum, String resetPassword) throws SQLException {
        String sql = "UPDATE User SET 用户密码 = ? WHERE 用户手机号 = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, resetPassword);
            pstmt.setString(2, phoneNum);

            pstmt.executeUpdate();
        }
    }

    // Admin 表的删除方法
    public void deleteAdmin(String 管理员用户名) throws SQLException {
        String sql = "DELETE FROM Admin WHERE 管理员用户名 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, 管理员用户名);
            pstmt.executeUpdate();
        }
    }


    // User 表的写入方法
    public void insertUser(long 客户ID, String 用户名, String 用户级别, String 用户注册时间,
                           BigDecimal 客户累计消费总金额, String 用户手机号, String 用户邮箱, String 用户密码) throws SQLException {
        String sql = "INSERT INTO User (客户ID, 用户名, 用户级别, 用户注册时间, 客户累计消费总金额, 用户手机号, 用户邮箱, 用户密码) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, 客户ID);
            pstmt.setString(2, 用户名);
            pstmt.setString(3, 用户级别);
            pstmt.setString(4, 用户注册时间); // 注意：这里是字符串
            pstmt.setBigDecimal(5, 客户累计消费总金额);
            pstmt.setString(6, 用户手机号);
            pstmt.setString(7, 用户邮箱);
            pstmt.setString(8, 用户密码);

            pstmt.executeUpdate();
        }
    }

    // User 表的更新方法
    public void updateUser(Long id, String 用户名, String 用户级别, String 用户注册时间, BigDecimal 客户累计消费总金额, String 用户手机号, String 用户邮箱, String 用户密码) throws SQLException {
        String sql = "UPDATE User SET 用户名 = ?, 用户级别 = ?, 用户注册时间 = ?, 客户累计消费总金额 = ?, 用户手机号 = ?, 用户邮箱 = ?, 用户密码 = ? WHERE 客户ID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, 用户名);
            pstmt.setString(2, 用户级别);
            pstmt.setString(3, 用户注册时间);
            pstmt.setBigDecimal(4, 客户累计消费总金额);
            pstmt.setString(5, 用户手机号);
            pstmt.setString(6, 用户邮箱);
            pstmt.setString(7, 用户密码);
            pstmt.setLong(8, id);

            pstmt.executeUpdate();
        }
    }

    // User 表的删除方法
    public void deleteUser(long 客户ID) throws SQLException {
        String sql = "DELETE FROM User WHERE 客户ID = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, 客户ID);
            pstmt.executeUpdate();
        }
    }

    public void deleteUserByPhoneNum(String 用户手机号) throws SQLException {
        String sql = "DELETE FROM User WHERE 用户手机号 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, 用户手机号);
            pstmt.executeUpdate();
        }
    }

    // Goods 表的写入方法
    public void insertGoods(long 商品编号, String 商品名称, String 生产厂家, String 生产日期,
                            String 型号, BigDecimal 进货价, BigDecimal 零售价, long 数量) throws SQLException {
        String sql = "INSERT INTO Goods (商品编号, 商品名称, 生产厂家, 生产日期, 型号, 进货价, 零售价, 数量) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, 商品编号);
            pstmt.setString(2, 商品名称);
            pstmt.setString(3, 生产厂家);
            pstmt.setString(4, 生产日期); // 注意：这里是字符串
            pstmt.setString(5, 型号);
            pstmt.setBigDecimal(6, 进货价);
            pstmt.setBigDecimal(7, 零售价);
            pstmt.setLong(8, 数量);

            pstmt.executeUpdate();
        }
    }

    // Goods 表的更新方法
    public void updateGoods(long 商品编号, String 商品名称, String 生产厂家, String 生产日期,
                            String 型号, BigDecimal 进货价, BigDecimal 零售价, long 数量) throws SQLException {
        String sql = "UPDATE Goods SET 商品名称 = ?, 生产厂家 = ?, 生产日期 = ?, 型号 = ?, 进货价 = ?, 零售价 = ?, 数量 = ? WHERE 商品编号 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, 商品名称);
            pstmt.setString(2, 生产厂家);
            pstmt.setString(3, 生产日期);
            pstmt.setString(4, 型号);
            pstmt.setBigDecimal(5, 进货价);
            pstmt.setBigDecimal(6, 零售价);
            pstmt.setLong(7, 数量);
            pstmt.setLong(8, 商品编号);

            pstmt.executeUpdate();
        }
    }

    // Goods 表的删除方法
    public void deleteGoods(long 商品编号) throws SQLException {
        String sql = "DELETE FROM Goods WHERE 商品编号 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, 商品编号);
            pstmt.executeUpdate();
        }
    }

    // ShoppingCart 表的写入方法
    public void insertShoppingCart(long 商品编号, String 商品名称, BigDecimal 商品零售价, long 购买数量) throws SQLException {
        String sql = "INSERT INTO ShoppingCart (商品编号, 商品名称, 商品零售价, 购买数量) VALUES (?, ?, ?, ?);";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, 商品编号);
            pstmt.setString(2, 商品名称);
            pstmt.setBigDecimal(3, 商品零售价);
            pstmt.setLong(4, 购买数量);

            pstmt.executeUpdate();
        }
    }

    // ShoppingCart 表的更新方法
    public void updateShoppingCart(long 商品编号, String 商品名称, BigDecimal 商品零售价, long 购买数量) throws SQLException {
        String sql = "UPDATE ShoppingCart SET 商品名称 = ?, 商品零售价 = ?, 购买数量 = ? WHERE 商品编号 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, 商品名称);
            pstmt.setBigDecimal(2, 商品零售价);
            pstmt.setLong(3, 购买数量);
            pstmt.setLong(4, 商品编号);

            pstmt.executeUpdate();
        }
    }

    // ShoppingCart 表的删除方法
    public void deleteShoppingCart(long 商品编号) throws SQLException {
        String sql = "DELETE FROM ShoppingCart WHERE 商品编号 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, 商品编号);
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
    public void insertShoppingHistory(String 购买时间, String 商品名称) throws SQLException {
        String sql = "INSERT INTO ShoppingHistory (购买时间, 商品名称) VALUES (?, ?);";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, 购买时间);
            pstmt.setString(2, 商品名称);

            pstmt.executeUpdate();
        }
    }

    // ShoppingHistory 表的更新方法
    public void updateShoppingHistory(String 原购买时间, String 原商品名称, String 新购买时间, String 新商品名称) throws SQLException {
        String sql = "UPDATE ShoppingHistory SET 购买时间 = ?, 商品名称 = ? WHERE 购买时间 = ? AND 商品名称 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, 新购买时间);
            pstmt.setString(2, 新商品名称);
            pstmt.setString(3, 原购买时间);
            pstmt.setString(4, 原商品名称);

            pstmt.executeUpdate();
        }
    }

    // ShoppingHistory 表的删除方法
    public void deleteShoppingHistory(String 购买时间, String 商品名称) throws SQLException {
        String sql = "DELETE FROM ShoppingHistory WHERE 购买时间 = ? AND 商品名称 = ?;";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, 购买时间);
            pstmt.setString(2, 商品名称);
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


