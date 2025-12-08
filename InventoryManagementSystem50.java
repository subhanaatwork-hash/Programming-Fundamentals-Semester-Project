import java.io.*;
import java.util.*;

public class InventoryManagementSystem50 {

    static Scanner sc = new Scanner(System.in);

    // --- Login credentials ---
    static String adminUser = "Admin";
    static String adminPass = "123";

    // Salespersons
    static String[] salesPersonsList = { "SP1", "SP2", "SP3", "SP4", "SP5", "SP6", "SP7", "SP8", "SP9", "SP10" };
    static String[] salesPersonPasswords = { "pass1", "pass2", "pass3", "pass4", "pass5", "pass6", "pass7", "pass8",
            "pass9", "pass10" };

    // --- Product Data ---
    static String[] products = new String[100];
    static String[] productIDs = new String[100];
    static int[] productStock = new int[100];
    static double[] productPrice = new double[100];
    static int productCount = 0;
    static final String PRODUCT_FILE = "products.txt";

    // --- Vendor Data ---
    static String[] vendorNames = new String[100];
    static String[] vendorContacts = new String[100];
    static int vendorCount = 0;
    static final String VENDOR_FILE = "vendors.txt";

    // --- Sales Records (simplified) ---
    static List<String> salesRecords = new ArrayList<>();

    public static void main(String[] args) {

        // Load data from files
        loadProductsFromFile();
        loadVendorsFromFile();

        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Sales Login");
            System.out.println("3. Exit");
            System.out.print("Enter option: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        adminLogin();
                        break;
                    case 2:
                        salesLogin();
                        break;
                    case 3:
                        System.out.println("Exiting System...");
                        saveProductsToFile();
                        saveVendorsToFile();
                        return;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    // ==================== LOGIN METHODS ====================

    static void adminLogin() {
        System.out.print("Enter Admin Username: ");
        String u = sc.nextLine();
        System.out.print("Enter Admin Password: ");
        String p = sc.nextLine();

        if (u.equals(adminUser) && p.equals(adminPass)) {
            System.out.println("Login successful!");
            adminDashboard();
        } else {
            System.out.println("Invalid credentials! Returning to Main Menu...");
        }
    }

    static void salesLogin() {
        System.out.print("Enter Sales ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        boolean authenticated = false;
        for (int i = 0; i < salesPersonsList.length; i++) {
            if (id.equals(salesPersonsList[i]) && pass.equals(salesPersonPasswords[i])) {
                authenticated = true;
                break;
            }
        }

        if (authenticated) {
            System.out.println("Login successful!");
            salesDashboard();
        } else {
            System.out.println("Invalid credentials! Returning to Main Menu...");
        }
    }

    // ==================== ADMIN DASHBOARD ====================

    static void adminDashboard() {
        while (true) {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Vendors");
            System.out.println("3. Manage Orders");
            System.out.println("4. View Reports");
            System.out.println("5. Logout");
            System.out.print("Enter option: ");

            try {
                int ch = Integer.parseInt(sc.nextLine());
                switch (ch) {
                    case 1:
                        manageProducts();
                        break;
                    case 2:
                        manageVendors();
                        break;
                    case 3:
                        manageOrders();
                        break;
                    case 4:
                        viewReports();
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // ==================== SALES DASHBOARD ====================

    static void salesDashboard() {
        while (true) {
            System.out.println("\n===== SALES DASHBOARD =====");
            System.out.println("1. Create Sales Order");
            System.out.println("2. View Products");
            System.out.println("3. Search Products");
            System.out.println("4. View Reports");
            System.out.println("5. Logout");
            System.out.print("Enter option: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        createSalesOrder();
                        break;
                    case 2:
                        viewProducts();
                        break;
                    case 3:
                        searchProduct();
                        break;
                    case 4:
                        viewReports();
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    // ==================== PRODUCT MANAGEMENT ====================

    static void manageProducts() {
        while (true) {
            System.out.println("\n--- MANAGE PRODUCTS ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Delete Product");
            System.out.println("4. Update Product");
            System.out.println("5. Search Product");
            System.out.println("6. Back");
            System.out.print("Enter option: ");

            try {
                int ch = Integer.parseInt(sc.nextLine());
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
                        updateProduct();
                        break;
                    case 5:
                        searchProduct();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    static void addProduct() {
        try {
            if (productCount >= products.length) {
                System.out.println("Cannot add more products!");
                return;
            }

            System.out.print("Enter Product ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Product Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Stock Quantity: ");
            int stock = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Product Price: ");
            double price = Double.parseDouble(sc.nextLine());

            productIDs[productCount] = id;
            products[productCount] = name;
            productStock[productCount] = stock;
            productPrice[productCount] = price;
            productCount++;

            saveProductsToFile();

            System.out.println("Product added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Stock and Price must be numbers.");
        }
    }

    static void viewProducts() {
        System.out.println("\n--- PRODUCT LIST ---");
        System.out.printf("%-5s %-10s %-20s %-10s %-10s\n", "No", "ID", "Name", "Stock", "Price");
        for (int i = 0; i < productCount; i++) {
            System.out.printf("%-5d %-10s %-20s %-10d %-10.2f\n", (i + 1), productIDs[i], products[i],
                    productStock[i], productPrice[i]);
        }
    }

    static void deleteProduct() {
        viewProducts();
        System.out.print("Enter product number to delete: ");
        try {
            int index = Integer.parseInt(sc.nextLine()) - 1;
            if (index >= 0 && index < productCount) {
                for (int i = index; i < productCount - 1; i++) {
                    productIDs[i] = productIDs[i + 1];
                    products[i] = products[i + 1];
                    productStock[i] = productStock[i + 1];
                    productPrice[i] = productPrice[i + 1];
                }
                productCount--;
                saveProductsToFile();
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Invalid product number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number.");
        }
    }

    static void updateProduct() {
        viewProducts();
        System.out.print("Enter product number to update: ");
        try {
            int index = Integer.parseInt(sc.nextLine()) - 1;
            if (index >= 0 && index < productCount) {
                System.out.print("Enter new name: ");
                products[index] = sc.nextLine();
                System.out.print("Enter new stock: ");
                productStock[index] = Integer.parseInt(sc.nextLine());
                System.out.print("Enter new price: ");
                productPrice[index] = Double.parseDouble(sc.nextLine());
                saveProductsToFile();
                System.out.println("Product updated!");
            } else {
                System.out.println("Invalid product number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Stock and price must be numbers.");
        }
    }

    static void searchProduct() {
        System.out.print("Enter Product ID or Name to search: ");
        String query = sc.nextLine();
        boolean found = false;
        for (int i = 0; i < productCount; i++) {
            if (products[i].equalsIgnoreCase(query) || productIDs[i].equalsIgnoreCase(query)) {
                System.out.printf("Found: %s | ID: %s | Stock: %d | Price: %.2f\n", products[i], productIDs[i],
                        productStock[i], productPrice[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Product not found.");
        }
    }

    // ==================== VENDOR MANAGEMENT ====================

    static void manageVendors() {
        while (true) {
            System.out.println("\n--- MANAGE VENDORS ---");
            System.out.println("1. Add Vendor");
            System.out.println("2. View Vendors");
            System.out.println("3. Delete Vendor");
            System.out.println("4. Back");
            System.out.print("Enter option: ");

            try {
                int ch = Integer.parseInt(sc.nextLine());
                switch (ch) {
                    case 1:
                        addVendor();
                        break;
                    case 2:
                        viewVendors();
                        break;
                    case 3:
                        deleteVendor();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    static void addVendor() {
        System.out.print("Enter Vendor Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Vendor Contact: ");
        String contact = sc.nextLine();

        vendorNames[vendorCount] = name;
        vendorContacts[vendorCount] = contact;
        vendorCount++;

        saveVendorsToFile();
        System.out.println("Vendor added successfully!");
    }

    static void viewVendors() {
        System.out.println("\n--- VENDORS ---");
        for (int i = 0; i < vendorCount; i++) {
            System.out.printf("%d. %s | Contact: %s\n", (i + 1), vendorNames[i], vendorContacts[i]);
        }
    }

    static void deleteVendor() {
        viewVendors();
        System.out.print("Enter vendor number to delete: ");
        try {
            int index = Integer.parseInt(sc.nextLine()) - 1;
            if (index >= 0 && index < vendorCount) {
                for (int i = index; i < vendorCount - 1; i++) {
                    vendorNames[i] = vendorNames[i + 1];
                    vendorContacts[i] = vendorContacts[i + 1];
                }
                vendorCount--;
                saveVendorsToFile();
                System.out.println("Vendor deleted successfully!");
            } else {
                System.out.println("Invalid vendor number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number.");
        }
    }

    // ==================== ORDER MANAGEMENT ====================

    static void manageOrders() {
        while (true) {
            System.out.println("\n--- MANAGE ORDERS ---");
            System.out.println("1. Create Purchase Order (Add Stock)");
            System.out.println("2. Create Sales Order (Reduce Stock)");
            System.out.println("3. Back");
            System.out.print("Enter option: ");

            try {
                int ch = Integer.parseInt(sc.nextLine());
                switch (ch) {
                    case 1:
                        createPurchaseOrder();
                        break;
                    case 2:
                        createSalesOrder();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    static void createPurchaseOrder() {
        viewProducts();
        System.out.print("Select product number to add stock: ");
        try {
            int index = Integer.parseInt(sc.nextLine()) - 1;
            if (index >= 0 && index < productCount) {
                System.out.print("Enter quantity to add: ");
                int qty = Integer.parseInt(sc.nextLine());
                if (qty > 0) {
                    productStock[index] += qty;
                    saveProductsToFile();
                    System.out.println("Stock updated!");
                } else {
                    System.out.println("Quantity must be positive.");
                }
            } else {
                System.out.println("Invalid product number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Must be a number.");
        }
    }

    static void createSalesOrder() {
        viewProducts();
        System.out.print("Select product number to sell: ");
        try {
            int index = Integer.parseInt(sc.nextLine()) - 1;
            if (index >= 0 && index < productCount) {
                System.out.print("Enter quantity to sell: ");
                int qty = Integer.parseInt(sc.nextLine());
                if (qty > 0 && qty <= productStock[index]) {
                    productStock[index] -= qty;
                    salesRecords.add(products[index] + " sold: " + qty);
                    saveProductsToFile();
                    System.out.println("Sales order completed!");
                } else {
                    System.out.println("Not enough stock or invalid quantity.");
                }
            } else {
                System.out.println("Invalid product number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }

    // ==================== REPORTS ====================

    static void viewReports() {
        System.out.println("\n--- STOCK REPORT ---");
        for (int i = 0; i < productCount; i++) {
            System.out.printf("%s | ID: %s | Stock: %d | Price: %.2f\n", products[i], productIDs[i], productStock[i],
                    productPrice[i]);
            if (productStock[i] < 5) {
                System.out.println("   ⚠ LOW STOCK WARNING!");
            }
        }
        System.out.println("--- SALES SUMMARY ---");
        for (String s : salesRecords) {
            System.out.println(s);
        }
    }

    // ==================== FILE HANDLING ====================

    static void saveProductsToFile() {
        try (FileWriter fw = new FileWriter(PRODUCT_FILE)) {
            for (int i = 0; i < productCount; i++) {
                fw.write(productIDs[i] + "," + products[i] + "," + productStock[i] + "," + productPrice[i] + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    static void loadProductsFromFile() {
        File file = new File(PRODUCT_FILE);
        if (!file.exists())
            return;

        try (Scanner fs = new Scanner(file)) {
            productCount = 0;
            while (fs.hasNextLine()) {
                String[] data = fs.nextLine().split(",");
                productIDs[productCount] = data[0];
                products[productCount] = data[1];
                productStock[productCount] = Integer.parseInt(data[2]);
                productPrice[productCount] = Double.parseDouble(data[3]);
                productCount++;
            }
        } catch (Exception e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    static void saveVendorsToFile() {
        try (FileWriter fw = new FileWriter(VENDOR_FILE)) {
            for (int i = 0; i < vendorCount; i++) {
                fw.write(vendorNames[i] + "," + vendorContacts[i] + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving vendors: " + e.getMessage());
        }
    }

    static void loadVendorsFromFile() {
        File file = new File(VENDOR_FILE);
        if (!file.exists())
            return;

        try (Scanner fs = new Scanner(file)) {
            vendorCount = 0;
            while (fs.hasNextLine()) {
                String[] data = fs.nextLine().split(",");
                vendorNames[vendorCount] = data[0];
                vendorContacts[vendorCount] = data[1];
                vendorCount++;
            }
        } catch (Exception e) {
            System.out.println("Error loading vendors: " + e.getMessage());
        }
    }
}
