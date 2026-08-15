package app;

import model.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Medicine m = new Medicine("Panadol 500mg", "Tablet", 30, 200, 20,
                                  LocalDate.of(2027, 5, 31), 1);
        System.out.println(m);
        System.out.println("Low stock? " + m.isLowStock());

        User u1 = new Admin(1, "admin", "admin123", "Noor Malik");
        User u2 = new SalesStaff(2, "staff1", "staff123", "Ali Raza");
        for (User u : new User[]{u1, u2})          // same call, different behavior
            System.out.println(u + " -> can delete? " + u.canDeleteMedicine());
    }
}