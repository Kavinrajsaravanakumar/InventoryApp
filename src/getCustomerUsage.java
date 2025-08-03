import java.util.List;
import java.util.Scanner;

public class getCustomerUsage {
    public static void userLogin(Scanner s,InventoryDAO dao) {
        System.out.println("Please login to enter...");
        System.out.println("1.Create Account");
        System.out.println("2.Login");
        System.out.println("3.Exit");

        int option = s.nextInt();
        s.nextLine();

        switch(option){
            case 1:
                System.out.println("Enter Username : \n");
                String newusername = s.nextLine();
                System.out.println("Enter Password(8) : \n");
                String newpassword = s.nextLine();

                boolean regSuccess = dao.registerUser(newusername, newpassword);
                if (regSuccess) {
                    System.out.println("Account created successfully! , now you can Login .");
                } else {
                    System.out.println("Account creation failed (username might exist).");
                }
                break;
            case 2:
                System.out.println("Enter Username : ");
                String username = s.nextLine();
                System.out.println("Enter Password(8) : ");
                String password = s.nextLine();

                boolean loginSuccess = dao.loginUser(username, password);
                if (loginSuccess) {
                    System.out.println("Login successful! Welcome, " + username+"\n\n");
                    showMenu(s,dao);
                } else {
                    System.out.println("Login failed. Invalid credentials.");
                }
                break;
            case 3:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public static void showMenu(Scanner s,InventoryDAO dao){

        while(true){
            System.out.println("Welcome....");
            System.out.println("1. View all Product");
            System.out.println("2. View Product by Category");
            System.out.println("3. View Product by Name");
            System.out.println("4. Buy Products");
            System.out.println("5. view Cart");
            System.out.println("6. Exit .");
            System.out.println("\n\nChoose an Option : ");

            int option = s.nextInt();
            s.nextLine();
            switch(option){
                case 1:
                    List<Product> allproducts = dao.getAllProducts();

                    if (allproducts.isEmpty()) {
                        System.out.println("No products found.");
                        return;
                    }

                    System.out.println("\n--- All Products ---");
                    System.out.printf("%-5s %-20s %-10s %-10s %-15s%n", "ID", "Name", "Quantity", "Price", "Category");
                    System.out.println("--------------------------------------------------------------------");

                    for (Product p : allproducts) {
                        System.out.printf("%-5d %-20s %-10d %-10.2f %-15s%n",
                                p.getId(), p.getName(), p.getQuantity(), p.getPrice(), p.getCategory());
                    }
                    break;


                case 2:
                    System.out.print("Enter the Category Name (Homecare, Food, Grocery, Kitchen, Stationary): ");
                    String cat = s.nextLine().trim();

                    if (cat.isEmpty()) {
                        System.out.println("Category cannot be empty.");
                        return;
                    }

                    List<Product> products = dao.getProductsByCategory(cat);

                    if (products.isEmpty()) {
                        System.out.println("No products found in category: " + cat);
                        return;
                    }

                    System.out.println("\n--- Products in Category: " + cat + " ---");
                    System.out.printf("%-5s %-20s %-10s %-10s %-15s%n", "ID", "Name", "Quantity", "Price", "Category");
                    System.out.println("--------------------------------------------------------------------");

                    for (Product p : products) {
                        System.out.printf("%-5d %-20s %-10d %-10.2f %-15s%n",
                                p.getId(), p.getName(), p.getQuantity(), p.getPrice(), p.getCategory());
                    }
                    break;


                case 3:
                    System.out.print("Enter the Product Name : ");
                    String name = s.nextLine().trim();

                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty.");
                        return;
                    }
                    Product p = dao.getProductByName(name);
                    if (p == null) {
                        System.out.println("Product not found with name: " + name);
                    } else {
                        System.out.println("\n--- Product Found ---");
                        System.out.printf("%-5s %-20s %-10s %-10s %-15s%n", "ID", "Name", "Quantity", "Price", "Category");
                        System.out.printf("%-5d %-20s %-10d %-10.2f %-15s%n",
                                p.getId(), p.getName(), p.getQuantity(), p.getPrice(), p.getCategory());
                    }
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
