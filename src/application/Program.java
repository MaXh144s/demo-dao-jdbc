package application;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import db.DbException;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    private static final Scanner sc = new Scanner(System.in);
    private static final SellerDao sellerDao = DaoFactory.createSellerDao();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Opção: ");
            int option = sc.nextInt();
            sc.nextLine();
            try {
                switch (option) {
                    case 1 -> findById();
                    case 2 -> findByDepartment();
                    case 3 -> findAll();
                    case 4 -> insert();
                    case 5 -> update();
                    case 6 -> delete();
                    case 0 -> running = false;
                }
            } catch (DbException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n==============================");
        System.out.println("      GERENCIADOR DE SELLERS  ");
        System.out.println("==============================");
        System.out.println(" 1 - Buscar por ID");
        System.out.println(" 2 - Buscar por departamento");
        System.out.println(" 3 - Listar todos");
        System.out.println(" 4 - Inserir");
        System.out.println(" 5 - Atualizar");
        System.out.println(" 6 - Remover");
        System.out.println(" 0 - Sair");
        System.out.println("------------------------------");
    }

    private static void findById() {
        System.out.print("Digite o Id para pesquisa: ");
        int id = sc.nextInt();
        sc.nextLine();
        Seller seller = sellerDao.findById(id);

        if (seller == null) {
            System.out.println("Nenhum seller encontrado com id " + id + ".");
            return;
        }
        System.out.println(seller);
    }

    private static void findByDepartment() {
        System.out.print("Digite o Id do department: ");
        int id = sc.nextInt();
        sc.nextLine();
        printList(sellerDao.findByDepartment(new Department(id, null)));
    }

    private static void findAll() {
        printList(sellerDao.findAll());
    }

    private static void insert() {
        Seller seller = instantiateSeller(null);
        sellerDao.insert(seller);
        System.out.println("Inserção concluída. Novo id = " + seller.getId());
    }

    private static void update() {
        System.out.print("Digite o Id do seller que deseja atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();
        sellerDao.update(instantiateSeller(id));
        System.out.println("Update concluído.");
    }

    private static void delete() {
        System.out.print("Digite o Id do seller que deseja remover: ");
        int id = sc.nextInt();
        sc.nextLine();
        sellerDao.deleteById(id);
        System.out.println("Deleção concluída.");
    }

    private static void printList(List<Seller> list) {
        if (list.isEmpty()) {
            System.out.println("Nenhum seller encontrado.");
            return;
        }
        list.forEach(System.out::println);
    }

    private static Seller instantiateSeller(Integer id) {
        System.out.print("Digite o nome: ");
        String name = sc.nextLine();

        System.out.print("Digite o email: ");
        String email = sc.nextLine();

        System.out.print("Digite a data de nascimento (yyyy-MM-dd): ");
        Date birthDate = Date.valueOf(sc.nextLine());

        System.out.print("Digite o salário base: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        System.out.print("Digite o Id do department: ");
        int depId = sc.nextInt();
        sc.nextLine();

        return new Seller(id, name, email, birthDate, salary, new Department(depId, null));
    }
}