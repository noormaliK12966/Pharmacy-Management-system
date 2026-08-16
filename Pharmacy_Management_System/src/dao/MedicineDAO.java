package dao;

import model.Medicine;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {

    public List<Medicine> getAll() {
        List<Medicine> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM medicines")) {
            while (rs.next()) {
                Medicine m = new Medicine(rs.getString("name"), rs.getString("category"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("min_stock"),
                        rs.getDate("expiry_date").toLocalDate(), rs.getInt("supplier_id"));
                m.setId(rs.getInt("id"));
                list.add(m);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    public boolean insert(Medicine m) {
    String sql = "INSERT INTO medicines (name, category, price, stock, min_stock, expiry_date, supplier_id) VALUES (?,?,?,?,?,?,?)";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, m.getName());   ps.setString(2, m.getCategory());
        ps.setDouble(3, m.getPrice());  ps.setInt(4, m.getStock());
        ps.setInt(5, m.getMinStock());  ps.setDate(6, Date.valueOf(m.getExpiryDate()));
        ps.setInt(7, m.getSupplierId());
        return ps.executeUpdate() > 0;
    } catch (SQLException e) { e.printStackTrace(); return false; }
}

public boolean updateStock(int id, int newStock) {
    String sql = "UPDATE medicines SET stock = ? WHERE id = ?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, newStock); ps.setInt(2, id);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) { e.printStackTrace(); return false; }
}

public boolean delete(int id) {
    String sql = "DELETE FROM medicines WHERE id = ?";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) { e.printStackTrace(); return false; }
}
}