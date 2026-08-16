package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Pharmacy Management System - Login");
        setSize(380, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 8, 8));

        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JButton btnLogin = new JButton("Login");

        add(new JLabel("Username:", SwingConstants.RIGHT)); add(txtUser);
        add(new JLabel("Password:", SwingConstants.RIGHT)); add(txtPass);
        add(new JPanel());                                   add(btnLogin);

        btnLogin.addActionListener(e -> {
            User u = new UserDAO().login(txtUser.getText().trim(),
                                         new String(txtPass.getPassword()));
            if (u == null) {
                JOptionPane.showMessageDialog(this, "Wrong username or password!");
            } else {
                dispose();
                new MainFrame(u).setVisible(true);
            }
        });
    }
}