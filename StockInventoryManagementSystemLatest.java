import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * StockInventoryManagementSystemUpdated (Single-file)
 *
 * Files:
 * - products.txt : ProductName,Stock
 * - vendors.txt : VendorName,Contact
 * - orders.txt : ORDER_TYPE,ProductName,Quantity,YYYY-MM-DD
 *
 * Features:
 * - Admin & Sales login
 * - Add/View/Delete/Update products (saved to products.txt)
 * - Add/View/Delete vendors (saved to vendors.txt)
 * - Create Purchase & Sales orders (saved to orders.txt)
 * - Robust input validation and exception handling
 */
public class StockInventoryManagementSystemUpdated {

    // Scanner & date formatter
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    // Login data
    private static final String ADMIN_USER = "Admin";
    private static final String ADMIN_PASS = "123";

    // Salespersons (static sample)
    private static final String[] SALES_PERSONS = {
            "SP1", "SP2", "SP3", "SP4", "SP5", "SP6", "SP7", "SP8", "SP9", "SP10"
    };
    private static final String[] SALES_PERSON_PW = {
            "pass1", "pass2", "pass3", "pass4", "pass5", "pass6", "pass7", "pass8", "pass9", "pass10"
    };

    // Files
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String VENDORS_FILE = "vendors.txt";
    private static final String ORDERS_FILE = "orders.txt";

    // In-memory data
    private static final List<Product> products = new ArrayList<>();
    private static final List<Vendor> vendors = new ArrayList<>();

    // Data classes
    private static class Product {
        String name;
        int stock;

        Product(String name, int stock) {
            this.name = name;
            this.stock = stock;
        }
    }

    private static class Vendor {
        String name;
        String contact;

        Vendor(String name, String contact) {
            this.name = name;
            this.contact = contact;
        }
    }

    public static void main(String[] args) {
        // Load data
        loadProductsFromFile();
        loadVendorsFromFile();

        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Sales person Login");
            System.out.println("3. Exit");
            System.out.print("Enter option: ");
            int choice = readIntValidated();

            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> salesPersonLogin();
                case 3 -> {
                    System.out.println("Exiting System... Saving data.");
                    saveProductsToFile();
                    saveVendorsToFile();
                    return;
                }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    // ---------- LOGIN ----------
    private static void adminLogin() {
        System.out.print("\nEnter Admin Username: ");
        String u = readLineTrim();
        System.out.print("Enter Password: ");
        String p = readLineTrim();

        if (u.equals(ADMIN_USER) && p.equals(ADMIN_PASS)) {
            System.out.println("Admin login successful.");
            adminDashboard();
        } else {
            System.out.println("Invalid Login!");
        }
    }

    private static void salesPersonLogin() {
        System.out.print("\nEnter SalesPerson ID: ");
        String id = readLineTrim();
        System.out.print("Enter Password: ");
        String pw = readLineTrim();

        boolean ok = false;
        for (int i = 0; i < SALES_PERSONS.length; i++) {
            if (SALES_PERSONS[i].equals(id) && SALES_PERSON_PW[i].equals(pw)) {
                ok = true;
                break;
            }
        }

        if (ok) {
            System.out.println("Login Successful!");
            salesDashboard();
        } else {
            System.out.println("Invalid Credentials! Returning to Main Menu...");
        }
    }

    // ---------- DASHBOARDS ----------
    private static void salesDashboard() {
        while (true) {
            System.out.println("\n===== SALES DASHBOARD =====");
            System.out.println("1- Create Sales Order");
            System.out.println("2- View Products");
            System.out.println("3- Search Products");
            System.out.println("4- View Reports");
            System.out.println("5- Logout");
            System.out.print("Enter your choice: ");
            int ch = readIntValidated();

            switch (ch) {
                case 1 -> createSalesOrder();
                case 2 -> viewProducts();
                case 3 -> searchProduct();
                case 4 -> viewReports();
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid Choice! Try again.");
            }
        }
    }

    private static void adminDashboard() {
        while (true) {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Vendors");
            System.out.println("3. Manage Orders");
            System.out.println("4. View Reports");
            System.out.println("5. Logout");
            System.out.print("Enter Option: ");
            int ch = readIntValidated();

            switch (ch) {
                case 1 -> manageProducts();
                case 2 -> manageVendors();
                case 3 -> manageOrders();
                case 4 -> viewReports();
                case 5 -> {
                    System.out.println("Logging out... Saving data.");
                    saveProductsToFile();
                    saveVendorsToFile();
                    return;
                }
                default -> System.out.println("Invalid Option!");
            }
        }
    }

    // ---------- Products ----------
    private static void manageProducts() {
        while (true) {
            System.out.println("\n===== MANAGE PRODUCTS =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Delete Product");
            System.out.println("4. Update Product Stock");
            System.out.println("5. Search Product");
            System.out.println("6. Back");
            System.out.print("Enter Option: ");
            int ch = readIntValidated();

            switch (ch) {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> deleteProduct();
                case 4 -> updateStock();
                case 5 -> searchProduct();
                case 6 -> {
                    saveProductsToFile();
                    return;
                }
                default -> System.out.println("Invalid Option!");
            }
        }
    }

    private static void addProduct() {
        try {
            System.out.print("Enter Product Name: ");
            String name = readLineTrim();
            System.out.print("Enter total Stock: ");
            int stock = readIntValidated();
            if (stock < 0) {
                System.out.println("Stock cannot be negative!");
                return;
            }
            products.add(new Product(name, stock));
            saveProductsToFile();
            System.out.println("Product added successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while adding product: " + e.getMessage());
        }
    }

    private static void viewProducts() {
        System.out.println("\n==============================");
        System.out.println("          PRODUCT LIST        ");
        System.out.println("==============================");
        System.out.printf("%-5s %-30s %-10s%n", "No", "Product Name", "Stock");
        System.out.println("--------------------------------------------------------");
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.printf("%-5d %-30s %-10d%n", i + 1, p.name, p.stock);
            }
        }
        System.out.println("--------------------------------------------------------");
    }

    private static void deleteProduct() {
        if (products.isEmpty()) {
            System.out.println("No products to delete.");
            return;
        }
        viewProducts();
        System.out.print("Enter product number to delete: ");
        int idx = readIntValidated() - 1;
        if (idx < 0 || idx >= products.size()) {
            System.out.println("Invalid index!");
            return;
        }
        products.remove(idx);
        saveProductsToFile();
        System.out.println("Product removed successfully!");
    }

    private static void updateStock() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        viewProducts();
        System.out.print("Select product to update: ");
        int idx = readIntValidated() - 1;
        if (idx < 0 || idx >= products.size()) {
            System.out.println("Invalid Product!");
            return;
        }
        System.out.print("Enter new stock quantity: ");
        int newStock = readIntValidated();
        if (newStock < 0) {
            System.out.println("Stock cannot be negative.");
            return;
        }
        products.get(idx).stock = newStock;
        saveProductsToFile();
        System.out.println("Stock Updated!");
    }

    private static void searchProduct() {
        System.out.print("Enter product name to search: ");
        String name = readLineTrim();
        for (Product p : products) {
            if (p.name.equalsIgnoreCase(name)) {
                System.out.println("Found: " + p.name + " | Stock: " + p.stock);
                return;
            }
        }
        System.out.println("Product Not Found!");
    }

    // ---------- Vendors ----------
    private static void manageVendors() {
        while (true) {
            System.out.println("\n===== MANAGE VENDORS =====");
            System.out.println("1. Add Vendor");
            System.out.println("2. View Vendors");
            System.out.println("3. Delete Vendor");
            System.out.println("4. Back");
            System.out.print("Enter Option: ");
            int ch = readIntValidated();

            switch (ch) {
                case 1 -> addVendor();
                case 2 -> viewVendors();
                case 3 -> deleteVendor();
                case 4 -> {
                    saveVendorsToFile();
                    return;
                }
                default -> System.out.println("Invalid Option!");
            }
        }
    }

    private static void addVendor() {
        System.out.print("Enter Vendor Name: ");
        String name = readLineTrim();
        System.out.print("Enter Vendor Contact: ");
        String contact = readLineTrim();
        vendors.add(new Vendor(name, contact));
        saveVendorsToFile();
        System.out.println("Vendor Added successfully!");
    }

    private static void viewVendors() {
        System.out.println("\n--- Vendor List ---");
        if (vendors.isEmpty()) {
            System.out.println("No vendors available.");
        } else {
            for (int i = 0; i < vendors.size(); i++) {
                Vendor v = vendors.get(i);
                System.out.println((i + 1) + ". " + v.name + " | Contact: " + v.contact);
            }
        }
    }

    private static void deleteVendor() {
        if (vendors.isEmpty()) {
            System.out.println("No vendors to delete.");
            return;
        }
        viewVendors();
        System.out.print("Enter vendor number to delete: ");
        int idx = readIntValidated() - 1;
        if (idx < 0 || idx >= vendors.size()) {
            System.out.println("Invalid Vendor!");
            return;
        }
        vendors.remove(idx);
        saveVendorsToFile();
        System.out.println("Vendor Deleted!");
    }

    // ---------- Orders ----------
    private static void manageOrders() {
        while (true) {
            System.out.println("\n===== MANAGE ORDERS =====");
            System.out.println("1. Create Purchase Order (Add Stock)");
            System.out.println("2. Create Sales Order (Reduce Stock)");
            System.out.println("3. Back to Admin Dashboard");
            System.out.print("Enter Option: ");
            int ch = readIntValidated();

            switch (ch) {
                case 1 -> createPurchaseOrder();
                case 2 -> createSalesOrder();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid Option!");
            }
        }
    }

    private static void createPurchaseOrder() {
        if (products.isEmpty()) {
            System.out.println("No products available! Add products first.");
            return;
        }
        viewProducts();
        System.out.print("Select product to add stock: ");
        int idx = readIntValidated() - 1;
        if (idx < 0 || idx >= products.size()) {
            System.out.println("Invalid product selection!");
            return;
        }
        System.out.print("Enter quantity to add: ");
        int qty = readIntValidated();
        if (qty <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }
        Product p = products.get(idx);
        p.stock += qty;
        saveProductsToFile();
        writeOrderToFile("PURCHASE", p.name, qty);
        System.out.println("Purchase Order Completed! Stock Updated.");
    }

    private static void createSalesOrder() {
        if (products.isEmpty()) {
            System.out.println("No products available! Add products first.");
            return;
        }
        viewProducts();
        System.out.print("Select product to sell: ");
        int idx = readIntValidated() - 1;
        if (idx < 0 || idx >= products.size()) {
            System.out.println("Invalid product selection!");
            return;
        }
        System.out.print("Enter quantity: ");
        int qty = readIntValidated();
        Product p = products.get(idx);
        if (qty <= 0 || qty > p.stock) {
            System.out.println("Not enough stock or invalid quantity!");
            return;
        }
        p.stock -= qty;
        saveProductsToFile();
        writeOrderToFile("SALE", p.name, qty);
        System.out.println("Sales Order Completed!");
    }

    // ---------- Reports ----------
    private static void viewReports() {
        System.out.println("\n==============================");
        System.out.println("         VIEW REPORTS");
        System.out.println("==============================");

        if (products.isEmpty()) {
            System.out.println("No products available to show reports.");
            return;
        }

        int totalProducts = products.size();
        int totalStock = 0;

        System.out.println("\nPRODUCT REPORT:");
        System.out.println("---------------------------------");

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            totalStock += p.stock;
            System.out.println((i + 1) + ". " + p.name + " | Stock: " + p.stock);
            if (p.stock < 5)
                System.out.println("   ⚠ LOW STOCK WARNING!");
        }

        System.out.println("---------------------------------");
        System.out.println("Total Products: " + totalProducts);
        System.out.println("Total Stock in Inventory: " + totalStock);
        System.out.println("---------------------------------");
    }

    // ---------- File IO ----------
    private static void loadProductsFromFile() {
        products.clear();
        File file = new File(PRODUCTS_FILE);
        if (!file.exists()) {
            // Provide default products to start with
            products.add(new Product("Notebook", 50));
            products.add(new Product("Ball Pen", 120));
            products.add(new Product("Marker", 40));
            saveProductsToFile();
            System.out.println("Products file not found. Created default products.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int ln = 0;
            while ((line = br.readLine()) != null) {
                ln++;
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length < 2) {
                    System.out.println("Skipping malformed product line " + ln + ": " + line);
                    continue;
                }
                String name = parts[0].trim();
                int stock;
                try {
                    stock = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid stock number on products file line " + ln + ": " + parts[1]);
                    continue;
                }
                products.add(new Product(name, stock));
            }
            System.out.println("Products loaded from file successfully!");
        } catch (IOException e) {
            System.out.println("Error loading products file: " + e.getMessage());
        }
    }

    private static void saveProductsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCTS_FILE))) {
            for (Product p : products) {
                bw.write(p.name + "," + p.stock);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    private static void loadVendorsFromFile() {
        vendors.clear();
        File file = new File(VENDORS_FILE);
        if (!file.exists()) {
            System.out.println("Vendors file not found. Start by adding vendors.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int ln = 0;
            while ((line = br.readLine()) != null) {
                ln++;
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length < 2) {
                    System.out.println("Skipping malformed vendor line " + ln + ": " + line);
                    continue;
                }
                String name = parts[0].trim();
                String contact = parts[1].trim();
                vendors.add(new Vendor(name, contact));
            }
            System.out.println("Vendors loaded from file successfully!");
        } catch (IOException e) {
            System.out.println("Error loading vendor file: " + e.getMessage());
        }
    }

    private static void saveVendorsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(VENDORS_FILE))) {
            for (Vendor v : vendors) {
                bw.write(v.name + "," + v.contact);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving vendors: " + e.getMessage());
        }
    }

    private static void writeOrderToFile(String orderType, String productName, int quantity) {
        String date = LocalDate.now().format(DATE_FMT);
        String record = orderType + "," + productName + "," + quantity + "," + date;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDERS_FILE, true))) {
            bw.write(record);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing order to file: " + e.getMessage());
        }
    }

    // ---------- Utilities ----------
    private static int readIntValidated() {
        while (true) {
            try {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    System.out.print("Please enter a number: ");
                    continue;
                }
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static String readLineTrim() {
        String line = sc.nextLine();
        if (line == null)
            return "";
        return line.trim();
    }
}
