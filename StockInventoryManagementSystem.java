import java.util.*;

public class StockInventoryManagementSystem {

    static Scanner sc = new Scanner(System.in);

    
    static String adminUser = "Admin";
    static String adminPass = "123";
    static String salesUser = "Sales_person";
    static String salesPass = "123";

    // Simple product list
    static ArrayList<String> products = new ArrayList<>();
    static ArrayList<Integer> productStock = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Admin Login");
        System.out.println("2. Sales person Login");
        System.out.println("3. Exit");
        System.out.print("Enter option: ");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Welcome to admin");
                adminLogin();
                break;
            case 2:
                System.out.println("Welcome to sales person");
                break;
            case 3:
                System.out.println("Exiting System...");
                return;
            default:
                System.out.println("Invalid Choice!");
        }
    }

    // ------------------ LOGIN SECTIONS ----------------------

    static void adminLogin() {
        System.out.print("\nEnter Admin Username: ");
        String u = sc.next();

        System.out.print("Enter Password: ");
        String p = sc.next();

        if (u.equals(adminUser) && p.equals(adminPass)) {
            adminDashboard();
        } else {
            System.out.println("Invalid Login!");
        }
    }

    // ------------------ ADMIN DASHBOARD ----------------------

    static void adminDashboard() {

        System.out.println("\n===== ADMIN DASHBOARD =====");
        System.out.println("1. Manage Products");
        System.out.println("2. Manage Vendors");
        System.out.println("3. Manage Orders");
        System.out.println("4. View Reports");
        System.out.println("5. Logout");
        System.out.print("Enter Option: ");

        int ch = sc.nextInt();

        switch (ch) {
            case 1:
                manageProducts();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid Option!");
        }
    }

    // ------------------ MANAGE PRODUCTS ----------------------

    static void manageProducts() {

        while (true) {
            System.out.println("\n===== MANAGE PRODUCTS =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Delete Product");
            System.out.println("4. Update Product Stock");
            System.out.println("5. Search Product");
            System.out.println("6. Back");
            System.out.print("Enter Option: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    updateStock();
                    break;
                case 5:
                    searchProduct();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid Option!");
            }
        }
    }

    /// ------------------ PRODUCT OPERATIONS ----------------------
    static void addProduct() {
        System.out.print("Enter Product Name: ");
        String name = sc.next();

        System.out.print("Enter Initial Stock: ");
        int stock = sc.nextInt();

        products.add(name);
        productStock.add(stock);

        System.out.println("Product Added!");
    }

    /// ------------------ VIEW PRODUCTS ----------------------
    static void viewProducts() {
        System.out.println("\n--- Product List ---");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i) + " | Stock: " + productStock.get(i));
        }
    }

    /// ------------------ DELETE PRODUCT ----------------------
    static void deleteProduct() {
        viewProducts();

        System.out.print("Enter product number to delete: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < products.size()) {
            products.remove(index);
            productStock.remove(index);
            System.out.println("Product Deleted!");
        } else {
            System.out.println("Invalid Product!");
        }
    }

    /// ------------------ UPDATE PRODUCT STOCK ----------------------
    static void updateStock() {
        viewProducts();

        System.out.print("Select product to update: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < products.size()) {
            System.out.print("Enter new stock quantity: ");
            int newStock = sc.nextInt();
            productStock.set(index, newStock);
            System.out.println("Stock Updated!");
        } else {
            System.out.println("Invalid Product!");
        }
    }

    /// ------------------ SEARCH PRODUCT ----------------------
    static void searchProduct() {
        System.out.print("Enter product name to search: ");
        String name = sc.next();

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equalsIgnoreCase(name)) {
                System.out.println("Found: " + products.get(i) + " | Stock: " + productStock.get(i));
                return;
            }
        }

        System.out.println("Product Not Found!");
    }
}

