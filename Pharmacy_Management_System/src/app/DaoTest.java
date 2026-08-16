package app;

import dao.MedicineDAO;
import model.Medicine;

public class DaoTest {
    public static void main(String[] args) {
        for (Medicine m : new MedicineDAO().getAll())
            System.out.println(m);
    }
}