package model;

public class SaleItem {
    private int id;
    private int saleId;
    private int medicineId;
    private String medicineName;
    private int quantity;
    private double unitPrice;

    public SaleItem(int medicineId, String medicineName, int quantity, double unitPrice) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double subtotal() { return quantity * unitPrice; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSaleId() { return saleId; }
    public void setSaleId(int s) { this.saleId = s; }
    public int getMedicineId() { return medicineId; }
    public String getMedicineName() { return medicineName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
}