import java.io.*;
import java.util.*;

public class StockInventoryManagementSystemUpdated {

    static Scanner sc = new Scanner(System.in);

    // login data
    static String adminUser = "Admin";
    static String adminPass = "123";

    // Initialize salespersons
    static String[] salesPersonsList = new String[] { "SP1", "SP2", "SP3", "SP4", "SP5", "SP6", "SP7", "SP8", "SP9",
            "SP10" };
    static String[] salesPersonPasswords = new String[] { "pass1", "pass2", "pass3", "pass4", "pass5", "pass6", "pass7",
            "pass8", "pass9", "pass10" };

    static String[] products = new String[100];
    static int[] productStock = new int[100];
    static int productCount = 0;

    static String[] vendorNames = new String[100];
    static String[] vendorContacts = new String[100];
    static int vendorCount = 0;

    static final String VENDOR_FILE = "vendors.txt";

    public static void main(String[] args) {
        while (true) {
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
                    salesPersonLogin();
                    break;
                case 3:
                    System.out.println("Exiting System...");
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    // ------------------ LOGIN SECTIONS ----------------------

    static void adminLogin() {
        System.out.print("\nEnter Admin Username: ");
        String u = sc.next();

        System.out.print("Enter Password: ");
        String p = sc.next();

        if (u.equals(adminUser) && p.equals(adminPass)) {
            initializeProducts();
            loadVendorsFromFile();
            adminDashboard();
        } else {
            System.out.println("Invalid Login!");

        }
    }

    // SALESPERSON LOGIN
    static void salesPersonLogin() {
        System.out.print("\nEnter SalesPerson ID: ");
        String salesPersonID = sc.next();

        System.out.print("Enter Password: ");
        String password = sc.next();

        boolean isAuthenticated = false;

        for (int i = 0; i < salesPersonsList.length; i++) {
            if (salesPersonID.equals(salesPersonsList[i]) && password.equals(salesPersonPasswords[i])) {
                isAuthenticated = true;
                break; // stop checking after match
            }
        }

        if (isAuthenticated) {
            System.out.println("Login Successful!");
            initializeProducts();
            salesDashboard();
        } else {
            System.out.println("Invalid Credentials! Returning to Main Menu...");
        }
    }

    // ---------- SALES DASHBOARD ----------
    public static void salesDashboard() {
        while (true) {
            System.out.println("\n================================  SALES DASHBOARD ================================");
            System.out.println("1- Create Sales Order");
            System.out.println("2- View Products");
            System.out.println("3- Search Products");
            System.out.println("4- View Reports");
            System.out.println("5- Logout");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            if (choice == 1) {
                createSalesOrder();
            } else if (choice == 2) {
                viewProducts();
            } else if (choice == 3) {
                // searchProduct();
            } else if (choice == 4) {
                // viewReports();
            } else if (choice == 5) {
                System.out.println("Logging out...");
                break;
            } else {
                System.out.println("Invalid Choice! Please try again.");
            }
        }
    }

    // ------------------ LOAD VENDORS FROM FILE ----------------------
    static void loadVendorsFromFile() {
        try {
            File file = new File(VENDOR_FILE);
            if (!file.exists())
                return;

            Scanner fs = new Scanner(file);

            vendorCount = 0;

            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                String[] data = line.split(",");
                vendorNames[vendorCount] = data[0];
                vendorContacts[vendorCount] = data[1];
                vendorCount++;
            }

            fs.close();
            System.out.println("Vendors loaded from file successfully!");

        } catch (Exception e) {
            System.out.println("Error loading vendor file: " + e.getMessage());
        }
    }

    // ------------------ INITIALIZE PRODUCTS ----------------------
    static void initializeProducts() {
        products[0] = "Notebook";
        productStock[0] = 50;
        products[1] = "Ball Pen";
        productStock[1] = 120;
        products[2] = "Marker";
        productStock[2] = 40;
        products[3] = "Highlighter";
        productStock[3] = 30;
        products[4] = "Eraser";
        productStock[4] = 80;
        products[5] = "Sharpener";
        productStock[5] = 60;
        products[6] = "Stapler";
        productStock[6] = 25;
        products[7] = "Glue Stick";
        productStock[7] = 35;
        products[8] = "Scale 12-inch";
        productStock[8] = 45;
        products[9] = "Paper Clips";
        productStock[9] = 200;

        productCount = 10;
    }

    // ------------------ ADMIN DASHBOARD ----------------------

    static void adminDashboard() {
        while (true) {
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
                case 2:
                    manageVenders();
                    break;
                case 3:
                    manageOrders();
                    break;
                case 4:
                    viewReports();
                    break;
                case 5:
                    Logout();
                    return;
                default:
                    System.out.println("Invalid Option!");
                    return;
            }
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

    static void addProduct() {
        try {
            if (productCount >= products.length) {
                System.out.println("Cannot add more products. Storage is full!");
                return;
            }

            System.out.print("Enter Product Name: ");
            String name = sc.next();

            System.out.print("Enter total Stock: ");

            // Validate integer input
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input! Stock must be a number.");
                sc.next();
                return;
            }

            int stock = sc.nextInt();

            if (stock < 0) {
                System.out.println("Stock cannot be negative!");
                return;
            }

            products[productCount] = name;
            productStock[productCount] = stock;
            productCount++;

            System.out.println("Product added successfully!");

        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding product.");
        }
    }

    /// ------------------ VIEW PRODUCTS ----------------------
    static void viewProducts() {
        System.out.println("\n==============================");
        System.out.println("          PRODUCT LIST        ");
        System.out.println("==============================");
        System.out.printf("%-5s %-20s %-10s\n", "No", "Product Name", "Stock");
        System.out.println("--------------------------------------------");

        for (int i = 0; i < productCount; i++) {
            System.out.printf("%-5d %-20s %-10d\n",
                    (i + 1), products[i], productStock[i]);
        }

        System.out.println("--------------------------------------------");
    }

    /// ------------------ DELETE PRODUCT ----------------------
    static void deleteProduct() {
        viewProducts();

        System.out.print("Enter product number to delete: ");
        int index = sc.nextInt() - 1;

        if (index < 0 || index >= productCount) {
            System.out.println("Invalid index!");
            return;
        }

        // Shift all elements left
        for (int i = index; i < productCount - 1; i++) {
            products[i] = products[i + 1];
            productStock[i] = productStock[i + 1];
        }

        // Reduce count
        productCount--;

        System.out.println("Product removed successfully!");
    }

    /// ------------------ UPDATE PRODUCT STOCK ----------------------
    static void updateStock() {
        viewProducts();

        System.out.print("Select product to update: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < productCount) {
            System.out.print("Enter new stock quantity: ");
            int newStock = sc.nextInt();

            productStock[index] = newStock;

            System.out.println("Stock Updated!");
        } else {
            System.out.println("Invalid Product!");
        }
    }

    /// ------------------ SEARCH PRODUCT ----------------------
    static void searchProduct() {
        System.out.print("Enter product name to search: ");
        String name = sc.next();

        for (int i = 0; i < products.length; i++) {
            if (products[i].equalsIgnoreCase(name)) {
                System.out.println("Found: " + products[i] + " | Stock: " + productStock[i]);
                return;
            }
        }

        System.out.println("Product Not Found!");
    }

    // ------------------ MANAGE VENDORS ----------------------
    static void manageVenders() {

        while (true) {
            System.out.println("\n===== MANAGE VENDORS =====");
            System.out.println("1. Add Vendor");
            System.out.println("2. View Vendors");
            System.out.println("3. Delete Vendor");
            System.out.println("4. Back");
            System.out.print("Enter Option: ");

            int ch = sc.nextInt();

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
                    System.out.println("Invalid Option!");
            }
        }
    }

    /// ------------------ VENDOR OPERATIONS ----------------------
    static void addVendor() {
        System.out.print("Enter Vendor Name: ");
        String name = sc.next();

        System.out.print("Enter Vendor Contact: ");
        String contact = sc.next();

        vendorNames[vendorCount] = name;
        vendorContacts[vendorCount] = contact;

        vendorCount++;

        saveVendorsToFile();
        System.out.println("Vendor Added successfully!");
    }

    /// ------------------ VIEW VENDORS ----------------------
    static void viewVendors() {
        System.out.println("\n--- Vendor List ---");
        for (int i = 0; i < vendorCount; i++) {
            System.out.println((i + 1) + ". " + vendorNames[i] + " | Contact: " +
                    vendorContacts[i]);
        }
    }

    /// ------------------ DELETE VENDOR ----------------------
    static void deleteVendor() {
        viewVendors();

        System.out.print("Enter vendor number to delete: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < vendorCount) {

            // Shift items left
            for (int i = index; i < vendorCount - 1; i++) {
                vendorNames[i] = vendorNames[i + 1];
                vendorContacts[i] = vendorContacts[i + 1];
            }
            vendorCount--;
            saveVendorsToFile();
            System.out.println("Vendor Deleted!");
        } else {
            System.out.println("Invalid Vendor!");
        }
    }

    // ------------------ MANAGE ORDERS ----------------------
    static void manageOrders() {
        while (true) {
            System.out.println("\n===== MANAGE ORDERS =====");
            System.out.println("1. Create Purchase Order (Add Stock)");
            System.out.println("2. Create Sales Order (Reduce Stock)");
            System.out.println("3. Back to Admin Dashboard");
            System.out.print("Enter Option: ");

            int ch = sc.nextInt();
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
                    System.out.println("Invalid Option!");
            }
        }
    }

    /// ------------------ ORDER OPERATIONS ----------------------
    static void createPurchaseOrder() {
        if (productCount == 0) {
            System.out.println("No products available! Add products first.");
            return;
        }

        viewProducts();
        System.out.print("Select product to add stock: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < productCount) {
            System.out.print("Enter quantity to add: ");
            int qty = sc.nextInt();

            if (qty > 0) {
                productStock[index] = productStock[index] + qty;
                System.out.println("Purchase Order Completed! Stock Updated.");
            } else {
                System.out.println("Quantity must be greater than zero.");
            }

        } else {
            System.out.println("Invalid product selection!");
        }
    }

    /// ------------------ CREATE SALES ORDER ----------------------
    static void createSalesOrder() {
        if (productCount == 0) {
            System.out.println("No products available! Add products first.");
            return;
        }

        viewProducts();

        System.out.print("Select product to sell: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < productCount) {

            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();

            if (qty > 0 && qty <= productStock[index]) {
                productStock[index] -= qty;
                System.out.println("Sales Order Completed!");
            } else {
                System.out.println("Not enough stock or invalid quantity!");
            }

        } else {
            System.out.println("Invalid product selection!");
        }
    }

    /// ------------------ VIEW REPORTS ----------------------
    static void viewReports() {

        System.out.println("\n==============================");
        System.out.println("         VIEW REPORTS");
        System.out.println("==============================");

        // If no products exist
        if (productCount == 0) {
            System.out.println("No products available to show reports.");
            System.out.println("Returning to Dashboard...");
            return;
        }

        int totalProducts = productCount;
        int totalStock = 0;

        System.out.println("\nPRODUCT REPORT:");
        System.out.println("---------------------------------");

        for (int i = 0; i < productCount; i++) {

            String name = products[i];
            int stock = productStock[i];
            totalStock += stock;

            System.out.println((i + 1) + ". " + name + " | Stock: " + stock);

            if (stock < 5) {
                System.out.println("   ⚠ LOW STOCK WARNING!");
            }
        }

        System.out.println("---------------------------------");
        System.out.println("Total Products: " + totalProducts);
        System.out.println("Total Stock in Inventory: " + totalStock);
        System.out.println("---------------------------------");

        System.out.println("Returning to Admin Dashboard...");
    }

    // ------------------ SAVE VENDORS TO FILE ----------------------
    static void saveVendorsToFile() {
        try {
            FileWriter fw = new FileWriter(VENDOR_FILE);

            for (int i = 0; i < vendorCount; i++) {
                fw.write(vendorNames[i] + "," + vendorContacts[i] + "\n");
            }

            fw.close();
            System.out.println("Vendor data saved!");

        } catch (Exception e) {
            System.out.println("Error saving vendors: " + e.getMessage());
        }
    }

    static void Logout() {
        System.out.println("Logging out...");
    }
}
