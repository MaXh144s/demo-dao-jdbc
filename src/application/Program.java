package application;

import java.sql.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
    public static void main(String[] args) {
        
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: Seller findById ===");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println("=== TEST 2: Seller findByDepartment ===");
        Department dp = new Department(2, null);

        List<Seller> list = sellerDao.findByDepartment(dp);

        list.forEach(System.out::println);

        System.out.println("=== TEST 3: Seller findByDepartment ===");
        list = sellerDao.findAll();

        list.forEach(System.out::println);

        System.out.println("=== TEST 4: Seller Insert ===");
        Seller newSeller = new Seller(null, "Greg", "Greg@gmail.com", new Date(0), 4000.0, dp);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("=== TEST 5: Seller update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Updated completed!");
    }
}
