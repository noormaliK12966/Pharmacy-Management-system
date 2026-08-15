package model;
public class Supplier {
    private int id; private String name; private String phone; private String address;
    public Supplier(int id, String name, String phone, String address) {
        this.id = id; this.name = name; this.phone = phone; this.address = address;
    }
    // Alt+Insert → Getter and Setter
    @Override public String toString() { return name + " | " + phone; }
}