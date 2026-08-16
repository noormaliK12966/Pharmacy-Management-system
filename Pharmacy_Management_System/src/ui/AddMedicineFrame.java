package ui;

import dao.MedicineDAO;
import model.Medicine;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AddMedicineFrame extends JFrame {
    public AddMedicineFrame(Runnable onSaved) {
        setTitle("Add Medicine");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 6, 6));

        JTextField txtName = new JTextField();
        JTextField txtCat  = new JTextField();
        JTextField txtPrice = new JTextField();
        JTextField txtStock = new JTextField();
        JTextField txtMin  = new JTextField();
        JTextField txtExp  = new JTextField("2027-12-31");
        JTextField txtSup  = new JTextField("1");

        add(new JLabel("Name:"));              add(txtName);
        add(new JLabel("Category:"));          add(txtCat);
        add(new JLabel("Price:"));             add(txtPrice);
        add(new JLabel("Stock:"));             add(txtStock);
        add(new JLabel("Min Stock:"));         add(txtMin);
        add(new JLabel("Expiry (YYYY-MM-DD):")); add(txtExp);
        add(new JLabel("Supplier ID:"));       add(txtSup);
        JButton btnSave = new JButton("Save");
        add(new JPanel()); add(btnSave);

        btnSave.addActionListener(e -> {
            try {
                Medicine m = new Medicine(txtName.getText().trim(), txtCat.getText().trim(),
                        Double.parseDouble(txtPrice.getText().trim()),
                        Integer.parseInt(txtStock.getText().trim()),
                        Integer.parseInt(txtMin.getText().trim()),
                        LocalDate.parse(txtExp.getText().trim()),
                        Integer.parseInt(txtSup.getText().trim()));
                if (new MedicineDAO().insert(m)) {
                    JOptionPane.showMessageDialog(this, "Medicine added!");
                    onSaved.run();
                    dispose();
                } else JOptionPane.showMessageDialog(this, "Insert failed!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Check fields! Date must be YYYY-MM-DD.");
            }
        });
    }
}