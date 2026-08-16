package ui;

import dao.MedicineDAO;
import dao.SaleDAO;
import model.Medicine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportsFrame extends JFrame {
    private final DefaultTableModel salesModel = new DefaultTableModel();
    private final DefaultTableModel itemsModel = new DefaultTableModel();
    private final JLabel lblStats = new JLabel(" ", SwingConstants.CENTER);

    public ReportsFrame() {
        setTitle("Sales History & Reports");
        setSize(850, 550);
        setLocationRelativeTo(null);

        salesModel.setColumnIdentifiers(new Object[]{"Sale ID", "Date", "Sold By", "Total (Rs)"});
        itemsModel.setColumnIdentifiers(new Object[]{"Medicine", "Qty", "Unit Price", "Subtotal"});
        JTable salesTable = new JTable(salesModel);
        JTable itemsTable = new JTable(itemsModel);

        salesTable.getSelectionModel().addListSelectionListener(ev -> {
            int row = salesTable.getSelectedRow();
            if (row >= 0) loadItems((int) salesModel.getValueAt(row, 0));
        });

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadAll());

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(salesTable), new JScrollPane(itemsTable));
        split.setResizeWeight(0.6);

        lblStats.setFont(lblStats.getFont().deriveFont(16f));
        add(lblStats, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);
        loadAll();
    }

    private void loadAll() {
        salesModel.setRowCount(0);
        for (Object[] r : new SaleDAO().getSalesRows()) salesModel.addRow(r);
        itemsModel.setRowCount(0);

        int low = 0, exp = 0;
        for (Medicine m : new MedicineDAO().getAll()) {
            if (m.isLowStock()) low++;
            if (m.isExpired()) exp++;
        }
        lblStats.setText("Today's Revenue: Rs. " + new SaleDAO().todayRevenue()
                + "   |   Low-stock medicines: " + low
                + "   |   Expired medicines: " + exp);
    }

    private void loadItems(int saleId) {
        itemsModel.setRowCount(0);
        for (Object[] r : new SaleDAO().getItemRows(saleId)) itemsModel.addRow(r);
    }
}