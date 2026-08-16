package model;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private int id; private String saleDate; private double total; private int userId;
    private List<SaleItem> items = new ArrayList<>();   // one bill HAS many lines

    public void addItem(SaleItem i) { items.add(i); }
    public List<SaleItem> getItems() { return items; }
    public double calculateTotal() {
        double t = 0;
        for (SaleItem i : items) t += i.getQuantity() * i.getUnitPrice();
        return t;
    }
    // Alt+Insert → getters/setters for id, saleDate, total, userId
}