package model;

import java.time.LocalDate;

public class Medicine {
    private int id;
    private String name;
    private String category;
    private double price;
    private int stock;
    private int minStock;
    private LocalDate expiryDate;
    private int supplierId;

    public Medicine(String name, String category, double price,
                    int stock, int minStock, LocalDate expiryDate, int supplierId) {
        setName(name); setCategory(category); setPrice(price);
        setStock(stock); setMinStock(minStock);
        this.expiryDate = expiryDate; this.supplierId = supplierId;
    }

    // ENCAPSULATION: the outside world can only touch data through these doors
    public void setPrice(double price) {
        if (price <= 0) throw new IllegalArgumentException("Price must be > 0");
        this.price = price;
    }
    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("Stock can't be negative");
        this.stock = stock;
    }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String c) { this.category = c; }
    public void setMinStock(int m) { this.minStock = m; }
    public int getMinStock() { return minStock; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public int getSupplierId() { return supplierId; }

    public boolean isLowStock() { return stock <= minStock; }
    public boolean isExpired()  { return expiryDate.isBefore(LocalDate.now()); }

    @Override
    public String toString() { return name + " | Rs." + price + " | stock:" + stock; }
}