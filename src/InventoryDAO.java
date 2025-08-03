import java.sql.*;
import java.util.*;
import java.security.MessageDigest;

public class InventoryDAO {
    private Connection conn;

    public InventoryDAO(Connection conn) {
        this.conn = conn;
    }

    public void addProduct(Product product) {
        try {
            String sql = "INSERT INTO products (product_name, quantity, price, category) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, product.getName());
            pst.setInt(2, product.getQuantity());
            pst.setDouble(3, product.getPrice());
            pst.setString(4, product.getCategory());
            pst.executeUpdate();
            System.out.println("Product added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try {
            String sql = "UPDATE products SET product_name = ?, quantity = ?, price = ?, category = ? WHERE product_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, product.getName());
            pst.setInt(2, product.getQuantity());
            pst.setDouble(3, product.getPrice());
            pst.setString(4, product.getCategory());
            pst.setInt(5, product.getId());
            pst.executeUpdate();
            System.out.println("Product updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product getProductById(int id) {
        try {
            String sql = "SELECT * FROM products WHERE product_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("category")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteProductById(int id) {
        try {
            String sql = "DELETE FROM products WHERE product_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Product deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM products";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("category")
                );
                productList.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM products WHERE LOWER(category) = LOWER(?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, category.trim());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("category")
                );
                productList.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    public boolean registerUser(String newusername,String newpassword) {
            try {
                String sql = "INSERT INTO users (username,password) VALUES (?,?)";
                PreparedStatement pst = conn.prepareStatement(sql);

                pst.setString(1,newusername);
                pst.setString(2,newpassword);
                pst.executeUpdate();

            }catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Username already exists.");
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
    }
    public boolean loginUser(String username,String password){
        try{
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,username);
            pst.setString(2,password);
            ResultSet rs = pst.executeQuery();
            return rs.next(); //returns user found or not
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Product getProductByName(String name){
        try {
            String sql = "SELECT * FROM products WHERE product_name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("category")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
