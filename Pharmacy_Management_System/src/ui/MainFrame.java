package ui;

import dao.MedicineDAO;
import model.Medicine;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    private final User user;
    private final DefaultTableModel model = new DefaultTableModel();
    private JTable table;

    public MainFrame(User user) {
        this.user = user;
        setTitle("Pharmacy - " + user.getFullName() + " (" + user.getRole() + ")");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcome = new JLabel("Welcome, " + user.getFullName()
                + "  |  Role: " + user.getRole(), SwingConstants.CENTER);
        welcome.setFont(welcome.getFont().deriveFont(20f));

        model.setColumnIdentifiers(new Object[]{"ID", "Name", "Category", "Price", "Stock", "Min Stock", "Status"});
        table = new JTable(model);

        JButton btnAdd = new JButton("Add Medicine");
        JButton btnRestock = new JButton("Restock +");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");

        // POLYMORPHISM: staff never even SEE add/delete
        if (!user.canDeleteMedicine()) { btnAdd.setVisible(false); btnDelete.setVisible(false); }

        btnAdd.addActionListener(e -> new AddMedicineFrame(this::loadMedicines).setVisible(true));
        btnRestock.addActionListener(e -> restock());
        btnDelete.addActionListener(e -> deleteMedicine());
        btnRefresh.addActionListener(e -> loadMedicines());

        JPanel buttons = new JPanel();
        buttons.add(btnAdd); buttons.add(btnRestock);
        buttons.add(btnDelete); buttons.add(btnRefresh);

        add(welcome, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        loadMedicines();
    }

    private void restock() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a medicine first!"); return; }
        int id = (int) model.getValueAt(row, 0);
        int current = (int) model.getValueAt(row, 4);
        String q = JOptionPane.showInputDialog(this, "Add how many units?", 50);
        if (q == null || q.trim().isEmpty()) return;
        new MedicineDAO().updateStock(id, current + Integer.parseInt(q.trim()));
        loadMedicines();
    }

    private void deleteMedicine() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a medicine first!"); return; }
        int id = (int) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete this medicine?") == JOptionPane.YES_OPTION) {
            new MedicineDAO().delete(id);
            loadMedicines();
        }
    }

    private void loadMedicines() {
        model.setRowCount(0);
        for (Medicine m : new MedicineDAO().getAll())
            model.addRow(new Object[]{m.getId(), m.getName(), m.getCategory(),
                    m.getPrice(), m.getStock(), m.getMinStock(),
                    m.isLowStock() ? "LOW STOCK" : "OK"});
    }
}