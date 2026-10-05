package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
    public static void main(String[] args) {
        
        SellerDao sl = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: Seller findById ===");
        Seller seller = sl.findById(3);

        System.out.println(seller);

        System.out.println("=== TEST 2: Seller findByDepartment ===");
        Department dp = new Department(2, null);

        List<Seller> list = sl.findByDepartment(dp);

        list.forEach(System.out::println);

        System.out.println("=== TEST 3: Seller findByDepartment ===");
        list = sl.findAll();

        list.forEach(System.out::println);
    }
}
