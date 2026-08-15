package model;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private int id; private String saleDate; private double total; private int userId;
    private List<Saleitem> items = new ArrayList<>();   // one bill HAS many lines

    public void addItem(Saleitem i) { items.add(i); }
    public List<Saleitem> getItems() { return items; }
    public double calculateTotal() {
        double t = 0;
        for (Saleitem i : items) t += i.getQuantity() * i.getUnitPrice();
        return t;
    }
    // Alt+Insert → getters/setters for id, saleDate, total, userId
}