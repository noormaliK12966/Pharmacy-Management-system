package dao;

import model.*;
import util.DBConnection;
import java.sql.*;

public class UserDAO {
    public User login(String username, String password) {
        String sql = "SELECT id, username, password, full_name, role FROM users WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if ("ADMIN".equals(rs.getString("role")))
                    return new Admin(rs.getInt("id"), rs.getString("username"),
                                     rs.getString("password"), rs.getString("full_name"));
                return new SalesStaff(rs.getInt("id"), rs.getString("username"),
                                      rs.getString("password"), rs.getString("full_name"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}