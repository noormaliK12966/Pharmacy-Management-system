package dao;

import model.*;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<User> getAllUsers() {
    List<User> list = new ArrayList<>();
    String sql = "SELECT id, username, password, full_name, role FROM users";
    try (Connection con = DBConnection.getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            if ("ADMIN".equals(rs.getString("role")))
                list.add(new Admin(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("full_name")));
            else
                list.add(new SalesStaff(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("full_name")));
        }
    } catch (SQLException e) { e.printStackTrace(); }
    return list;
}

public boolean addUser(String username, String password, String fullName, String role) {
    String sql = "INSERT INTO users (username, password, full_name, role) VALUES (?,?,?,?)";
    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, username); ps.setString(2, password);
        ps.setString(3, fullName); ps.setString(4, role);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) { e.printStackTrace(); return false; }
}
}