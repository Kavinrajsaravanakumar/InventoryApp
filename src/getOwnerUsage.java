import java.util.*;

public class getOwnerUsage {
        public static void showMenu(Scanner s,InventoryDAO dao){
            while(true){
                System.out.println("Welcome....");
                System.out.println("1. Add Product");
                System.out.println("2. Update Product");
                System.out.println("3. Delete Product");
                System.out.println("4. View All Products");
                System.out.println("5. View Products by Category");
                System.out.println("6. Search Product by ID ");
                System.out.println("\n\n Chose an Option : ");

                int option = s.nextInt();

                switch(option){
                    case 1:
                        addProduct(s,dao);
                        break;
                    case 2:
                        updateProduct(s,dao);
                        break;
                    case 3:
                        deleteProduct(s,dao);
                        break;
                    case 4:
                        viewallProduct(s,dao);
                        break;
                    case 5:
                        viewCategoryProduct(s,dao);
                        break;
                    case 6:
                        System.out.println("Enter Product ID : ");
                        int Id = s.nextInt();
                        searchProductById(s,dao,Id);
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        }
        public static void addProduct(Scanner s,InventoryDAO dao){

            System.out.println("Add Product");

            System.out.println("Enter Name : ");
            String name = s.nextLine();
            s.nextLine();
            System.out.println("Enter Quantity : ");
            int qty = s.nextInt();
            s.nextLine();
            System.out.println("Enter Price : ");
            double price = s.nextDouble();
            s.nextLine();
            System.out.println("Enter Category (Homecare , Food , Grocery , Kitchen , Stationary)");
            String category = s.nextLine();

            dao.addProduct(new Product(0, name, qty, price, category));

        }
        public static void updateProduct(Scanner s, InventoryDAO dao){
            System.out.print("Enter Product ID to Update :");
            int id = s.nextInt();
            s.nextLine();

            Product existingProduct = dao.getProductById(id);

            if (existingProduct == null) {
                System.out.println("Product with ID " + id + " not found.");
                return;
            }

            System.out.println("\n--- Existing Product Details ---");
            System.out.println("Name     : " + existingProduct.getName());
            System.out.println("Quantity : " + existingProduct.getQuantity());
            System.out.println("Price    : " + existingProduct.getPrice());
            System.out.println("Category : " + existingProduct.getCategory());

            System.out.println("\n--- Enter New Details ---");
            System.out.print("Enter New Name (press Enter to keep same): ");
            String name = s.nextLine();
            if (name.trim().isEmpty()) name = existingProduct.getName();

            System.out.print("Enter New Quantity (or -1 to keep same): ");
            int qty = s.nextInt();
            if (qty == -1) qty = existingProduct.getQuantity();

            System.out.print("Enter New Price (or -1 to keep same): ");
            double price = s.nextDouble();
            if (price == -1) price = existingProduct.getPrice();
            s.nextLine();

            System.out.print("Enter New Category (press Enter to keep same): ");
            String category = s.nextLine();
            if (category.trim().isEmpty()) category = existingProduct.getCategory();

            dao.updateProduct(new Product(id, name, qty, price, category));

        }
        public static void deleteProduct(Scanner s, InventoryDAO dao){
            System.out.print("Enter Product ID to Update :");
            int id = s.nextInt();
            s.nextLine();

            Product product = dao.getProductById(id);

            if (product == null) {
                System.out.println("Product with ID " + id + " not found.");
                return;
            }

            // Show product details
            System.out.println("\n--- Product details ---");
            System.out.println("Name     : " + product.getName());
            System.out.println("Quantity : " + product.getQuantity());
            System.out.println("Price    : " + product.getPrice());
            System.out.println("Category : " + product.getCategory());

            System.out.print("\nAre you sure you want to delete this product? (yes/no): ");
            String confirm = s.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                dao.deleteProductById(id);

            } else {
                System.out.println("Deletion cancelled.");
            }

        }
        public static void viewallProduct(Scanner s, InventoryDAO dao){
            List<Product> products = dao.getAllProducts();

            if (products.isEmpty()) {
                System.out.println("No products found.");
                return;
            }

            System.out.println("\n--- All Products ---");
            System.out.printf("%-5s %-20s %-10s %-10s %-15s%n", "ID", "Name", "Quantity", "Price", "Category");
            System.out.println("--------------------------------------------------------------------");

            for (Product p : products) {
                System.out.printf("%-5d %-20s %-10d %-10.2f %-15s%n",
                        p.getId(), p.getName(), p.getQuantity(), p.getPrice(), p.getCategory());
            }
        }

    public static void viewCategoryProduct(Scanner s, InventoryDAO dao) {
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
    }

    public static void searchProductById(Scanner s, InventoryDAO dao, int Id){
            System.out.println("Enter Product ID : ");
            int id = s.nextInt();
            s.nextLine();

            Product product = dao.getProductById(id);

            if (product == null) {
                System.out.println("Product with ID " + id + " not found.");
                return;
            }

            System.out.println("\n--- Product Details ---");
            System.out.println("ID       : " + product.getId());
            System.out.println("Name     : " + product.getName());
            System.out.println("Quantity : " + product.getQuantity());
            System.out.println("Price    : " + product.getPrice());
            System.out.println("Category : " + product.getCategory());
        }
}
