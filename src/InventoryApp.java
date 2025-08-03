import java.util.*;
import java.sql.*;

public class InventoryApp {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/Inventory";
        String user = "root";
        String password = "Kavinraj@mysql";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            InventoryDAO dao = new InventoryDAO(conn);

            while (true) {
                System.out.println("\n=== Welcome to InventoryMarket ===");
                System.out.println("Enter as:");
                System.out.println("1. Customer");
                System.out.println("2. Owner");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int role = s.nextInt();
                s.nextLine();
                System.out.println("\n\n");

                switch (role) {
                    case 1:
                        getCustomerUsage.userLogin(s, dao);
                        break;
                    case 2:
                        getOwnerUsage.showMenu(s, dao);
                        break;
                    case 3:
                        System.out.println("Thank you for Visiting !!");
                        conn.close();
                        s.close();
                        System.exit(0);
                    default:
                        System.out.println("Enter a valid choice number.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error connecting to database.");
            e.printStackTrace();
        }
    }
}
