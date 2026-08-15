package model;

public abstract class User {
    private int id;
    private String username;
    private String password;
    private String fullName;

    protected User(int id, String username, String password, String fullName) {
        this.id = id; this.username = username;
        this.password = password; this.fullName = fullName;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }

    public abstract boolean canDeleteMedicine();  // each child answers differently
    public abstract String getRole();

    @Override
    public String toString() { return fullName + " (" + getRole() + ")"; }
}