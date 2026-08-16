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
}