package model;
public class Saleitem {
    private int id; private int saleId; private int medicineId;
    private int quantity; private double unitPrice;

    public Saleitem(int medicineId, int quantity, double unitPrice) {
        this.medicineId = medicineId; this.quantity = quantity; this.unitPrice = unitPrice;
    }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public int getMedicineId() { return medicineId; }
    // Alt+Insert → the rest
}