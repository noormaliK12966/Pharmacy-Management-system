package ui;

import dao.MedicineDAO;
import dao.SaleDAO;
import model.Medicine;
import model.SaleItem;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PosFrame extends JFrame {
    private final User user;
    private final DefaultTableModel medModel = new DefaultTableModel();
    private final DefaultTableModel cartModel = new DefaultTableModel();
    private JTable medTable, cartTable;
    private JLabel lblTotal = new JLabel("Total: Rs. 0.0", SwingConstants.RIGHT);
    private JSpinner spnQty = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));

    public PosFrame(User user) {
        this.user = user;
        setTitle("POS - New Sale (" + user.getFullName() + ")");
        setSize(950, 500);
        setLocationRelativeTo(null);

        medModel.setColumnIdentifiers(new Object[]{"ID", "Name", "Price", "Stock"});
        cartModel.setColumnIdentifiers(new Object[]{"ID", "Name", "Qty", "Price", "Subtotal"});
        medTable = new JTable(medModel);
        cartTable = new JTable(cartModel);

        JButton btnAdd = new JButton("Add to Cart");
        JButton btnCheckout = new JButton("Checkout");
        btnAdd.addActionListener(e -> addToCart());
        btnCheckout.addActionListener(e -> checkout());

        JPanel left = new JPanel(new BorderLayout());
        left.add(new JScrollPane(medTable), BorderLayout.CENTER);
        JPanel qtyPanel = new JPanel();
        qtyPanel.add(new JLabel("Qty:")); qtyPanel.add(spnQty); qtyPanel.add(btnAdd);
        left.add(qtyPanel, BorderLayout.SOUTH);

        JPanel right = new JPanel(new BorderLayout());
        right.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.add(lblTotal, BorderLayout.CENTER);
        totalPanel.add(btnCheckout, BorderLayout.EAST);
        right.add(totalPanel, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.5);
        add(split, BorderLayout.CENTER);
        loadMedicines();
    }

    private void loadMedicines() {
        medModel.setRowCount(0);
        for (Medicine m : new MedicineDAO().getAll())
            medModel.addRow(new Object[]{m.getId(), m.getName(), m.getPrice(), m.getStock()});
    }

    private void addToCart() {
        int row = medTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a medicine!"); return; }
        int id = (int) medModel.getValueAt(row, 0);
        String name = (String) medModel.getValueAt(row, 1);
        double price = (double) medModel.getValueAt(row, 2);
        int stock = (int) medModel.getValueAt(row, 3);
        int qty = (int) spnQty.getValue();
        if (qty > stock) { JOptionPane.showMessageDialog(this, "Only " + stock + " in stock!"); return; }
        cartModel.addRow(new Object[]{id, name, qty, price, qty * price});
        updateTotal();
    }

    private void updateTotal() {
        double t = 0;
        for (int i = 0; i < cartModel.getRowCount(); i++)
            t += (double) cartModel.getValueAt(i, 4);
        lblTotal.setText("Total: Rs. " + t);
    }

    private void checkout() {
        if (cartModel.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Cart is empty!"); return; }
        List<SaleItem> items = new ArrayList<>();
        double total = 0;
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            int id = (int) cartModel.getValueAt(i, 0);
            String name = (String) cartModel.getValueAt(i, 1);
            int qty = (int) cartModel.getValueAt(i, 2);
            double price = (double) cartModel.getValueAt(i, 3);
            items.add(new SaleItem(id, name, qty, price));
            total += qty * price;
        }
        if (new SaleDAO().checkout(user.getId(), items, total)) {
            JOptionPane.showMessageDialog(this, "Sale saved! Total Rs. " + total);
            cartModel.setRowCount(0);
            updateTotal();
            loadMedicines();
        } else {
            JOptionPane.showMessageDialog(this, "Checkout failed!");
        }
    }
}