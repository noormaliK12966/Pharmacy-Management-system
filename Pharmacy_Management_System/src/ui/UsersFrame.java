package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsersFrame extends JFrame {
    private final DefaultTableModel model = new DefaultTableModel();

    public UsersFrame() {
        setTitle("Manage Users (Admin)");
        setSize(750, 400);
        setLocationRelativeTo(null);

        model.setColumnIdentifiers(new Object[]{"ID", "Username", "Full Name", "Role"});
        JTable table = new JTable(model);

        JTextField txtUser = new JTextField(8);
        JTextField txtPass = new JTextField(8);
        JTextField txtName = new JTextField(10);
        JComboBox<String> cmbRole = new JComboBox<>(new String[]{"STAFF", "ADMIN"});
        JButton btnAdd = new JButton("Add User");

        JPanel form = new JPanel();
        form.add(new JLabel("Username:"));  form.add(txtUser);
        form.add(new JLabel("Password:"));  form.add(txtPass);
        form.add(new JLabel("Full Name:")); form.add(txtName);
        form.add(new JLabel("Role:"));      form.add(cmbRole);
        form.add(btnAdd);

        btnAdd.addActionListener(e -> {
            boolean ok = new UserDAO().addUser(txtUser.getText().trim(), txtPass.getText().trim(),
                    txtName.getText().trim(), (String) cmbRole.getSelectedItem());
            if (ok) { JOptionPane.showMessageDialog(this, "User added!"); load(); }
            else JOptionPane.showMessageDialog(this, "Failed (username may already exist).");
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(form, BorderLayout.SOUTH);
        load();
    }

    private void load() {
        model.setRowCount(0);
        for (User u : new UserDAO().getAllUsers())
            model.addRow(new Object[]{u.getId(), u.getUsername(), u.getFullName(), u.getRole()});
    }
}