package model;
public class SalesStaff extends User {
    public SalesStaff(int id, String u, String p, String f) { super(id, u, p, f); }
    @Override public boolean canDeleteMedicine() { return false; }
    @Override public String getRole() { return "STAFF"; }
}