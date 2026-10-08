package application;

import java.util.List;
import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

    private static final Scanner sc = new Scanner(System.in);
    private static final DepartmentDao depDao = DaoFactory.createDepartmentDao();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Opção: ");
            int option = sc.nextInt();
            switch (option) {
                case 1 -> findById();
                case 2 -> findAll();
                case 3 -> insert();
                case 4 -> update();
                case 5 -> delete();
                case 0 -> running = false;
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n==============================");
        System.out.println("      GERENCIADOR DE DEPARTMENTS  ");
        System.out.println("==============================");
        System.out.println(" 1 - Buscar por ID");
        System.out.println(" 2 - Listar todos");
        System.out.println(" 3 - Inserir");
        System.out.println(" 4 - Atualizar");
        System.out.println(" 5 - Remover");
        System.out.println(" 0 - Sair");
        System.out.println("------------------------------");
    }

    public static void findById() {
        System.out.print("Digite o Id para pesquisa: ");
        int id = sc.nextInt();
        Department dep = depDao.findById(id);

        if (dep == null) {
            System.out.println("Nenhum deparment encontrado com id " + id + ".");
            return;
        }
        
        System.out.println(dep);
    }

    private static void findAll() {
        printList(depDao.findAll());
    }

    private static void insert() {
        depDao.insert(instanciateDepartment());
        System.out.println("Inserção concluída");
    }

    public static void update() {
        depDao.update(instanciateDepartment());
        System.out.println("Update concluído.");
    }

    public static void delete() {
        System.out.print("Digite o Id do department que deseja remover: ");
        int id = sc.nextInt();
        depDao.deleteById(id);
        System.out.println("Deleção concluída.");
    }

    private static void printList(List<Department> list) {
        list.forEach(System.out::println);
    }

    private static Department instanciateDepartment() {
        System.out.print("Digite o ID do department: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Digite o nome do department: ");
        String name = sc.nextLine();

        return new Department(id, name);
    }

}
