package dao;

import model.SaleItem;
import util.DBConnection;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class SaleDAO {

    public boolean checkout(int staffId, List<SaleItem> items, double total) {
      String sqlSale  = "INSERT INTO sales (user_id, total_amount) VALUES (?,?)";
        String sqlItem  = "INSERT INTO sale_items (sale_id, medicine_id, quantity, unit_price) VALUES (?,?,?,?)";
        String sqlStock = "UPDATE medicines SET stock = stock - ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                int saleId;
                try (PreparedStatement psSale = con.prepareStatement(sqlSale, Statement.RETURN_GENERATED_KEYS)) {
                    psSale.setInt(1, staffId);
                    psSale.setDouble(2, total);
                    psSale.executeUpdate();
                    try (ResultSet keys = psSale.getGeneratedKeys()) { keys.next(); saleId = keys.getInt(1); }
                }
                try (PreparedStatement psItem = con.prepareStatement(sqlItem);
                     PreparedStatement psStock = con.prepareStatement(sqlStock)) {
                    for (SaleItem it : items) {
                        psItem.setInt(1, saleId);  psItem.setInt(2, it.getMedicineId());
                        psItem.setInt(3, it.getQuantity()); psItem.setDouble(4, it.getUnitPrice());
                        psItem.addBatch();
                        psStock.setInt(1, it.getQuantity()); psStock.setInt(2, it.getMedicineId());
                        psStock.addBatch();
                    }
                    psItem.executeBatch();
                    psStock.executeBatch();
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    public List<Object[]> getSalesRows() {
    List<Object[]> rows = new ArrayList<>();
    String sql = "SELECT s.id, s.sale_date, u.username, s.total_amount " +
                 "FROM sales s JOIN users u ON s.user_id = u.id ORDER BY s.id DESC";
    try (Connection con = DBConnection.getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        while (rs.next())
            rows.add(new Object[]{rs.getInt("id"), rs.getString("sale_date"),
                                  rs.getString("username"), rs.getDouble("total_amount")});
    } catch (SQLException e) { e.printStackTrace(); }
    return rows;
}

public List<Object[]> getItemRows(int saleId) {
    List<Object[]> rows = new ArrayList<>();
    String sql = "SELECT m.name, si.quantity, si.unit_price FROM sale_items si " +
                 "JOIN medicines m ON si.medicine_id = m.id WHERE si.sale_id = ?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, saleId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int q = rs.getInt("quantity"); double p = rs.getDouble("unit_price");
            rows.add(new Object[]{rs.getString("name"), q, p, q * p});
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return rows;
}

public double todayRevenue() {
    String sql = "SELECT COALESCE(SUM(total_amount),0) FROM sales WHERE DATE(sale_date) = CURDATE()";
    try (Connection con = DBConnection.getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        if (rs.next()) return rs.getDouble(1);
    } catch (SQLException e) { e.printStackTrace(); }
    return 0;
}
}