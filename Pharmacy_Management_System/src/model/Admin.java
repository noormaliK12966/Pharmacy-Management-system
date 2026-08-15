package model;
public class Admin extends User {
    public Admin(int id, String u, String p, String f) { super(id, u, p, f); }
    @Override public boolean canDeleteMedicine() { return true; }
    @Override public String getRole() { return "ADMIN"; }
}