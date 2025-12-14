import java.io.*;
import java.util.*;

public class InventoryManagementSystemFinal {

    static Scanner sc = new Scanner(System.in);

    // ---------- Login ----------
    static final String ADMIN_USER = "Admin";
    static final String ADMIN_PASS = "123";

    static final String[] SALES_IDS = { "SP1", "SP2", "SP3", "SP4", "SP5", "SP6", "SP7", "SP8", "SP9", "SP10" };
    static final String[] SALES_PASSES = { "pass1", "pass2", "pass3", "pass4", "pass5", "pass6", "pass7", "pass8",
            "pass9", "pass10" };

    // ---------- Products ----------
    static final int MAX_PRODUCTS = 100;
    static String[] productIDs = new String[MAX_PRODUCTS];
    static String[] productNames = new String[MAX_PRODUCTS];
    static String[] productStock = new String[MAX_PRODUCTS]; 
    static String[] productPrice = new String[MAX_PRODUCTS]; 
    static int productCount = 0;
    static final String PRODUCT_FILE = "products.txt";

    // ---------- Vendors ----------
    static final int MAX_VENDORS = 100;
    static String[] vendorNames = new String[MAX_VENDORS];
    static String[] vendorContacts = new String[MAX_VENDORS];
    static int vendorCount = 0;
    static final String VENDOR_FILE = "vendors.txt";

    // ---------- Sales ----------
    static final int MAX_SALES = 1000;
    static String[] salesProducts = new String[MAX_SALES];
    static String[] salesQuantity = new String[MAX_SALES];
    static int salesCount = 0;

    // ---------- Purchases ----------
    static final int MAX_PURCHASES = 1000;
    static String[] purchaseProducts = new String[MAX_PURCHASES];
    static String[] purchaseQuantity = new String[MAX_PURCHASES];
    static int purchaseCount = 0;


    public static void main(String[] args) {
        loadProductsFromFile();
        loadVendorsFromFile();


        // If products file empty, initialize with 5 default products
        if (productCount == 0) {
            addDefaultProducts();
            saveProductsToFile();
        }

        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Sales Login");
            System.out.println("3. Exit");
            System.out.print("Enter option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": adminLogin(); break;
                case "2": salesLogin(); break;
                case "3":
                    System.out.println("Exiting... Saving data.");
                    saveProductsToFile();
                    saveVendorsToFile();
                    return;
                default: System.out.println("Invalid option."); 
            }
        }
    }

    // ---------- ADMIN LOGIN ----------
static void adminLogin() {
    boolean loggedIn = false;
    int attempts = 0;

    while (!loggedIn && attempts < 3) {  // Allowing max 3 attempts
        try {
            System.out.print("Enter Admin Username: ");
            String u = sc.nextLine().trim();
            System.out.print("Enter Admin Password: ");
            String p = sc.nextLine().trim();

            if (u.equalsIgnoreCase(ADMIN_USER) && p.equalsIgnoreCase(ADMIN_PASS)) {
                System.out.println("Admin login successful!");
                adminDashboard();
                loggedIn = true;
            } else {
                System.out.println("Invalid admin credentials. Try again.");
                attempts++;
            }
        } catch (Exception e) {
            System.out.println("An error occurred. Please enter again.");
            sc.nextLine(); 
            attempts++;
        }
    }

    if (!loggedIn) {
        System.out.println("Maximum login attempts reached. Returning to main menu.");
    }
}

// ---------- SALES LOGIN ----------
static void salesLogin() {
    boolean loggedIn = false;
    int attempts = 0;

    while (!loggedIn && attempts < 3) {  // Allowing them max 3 attempts
        try {
            System.out.print("Enter Sales ID: ");
            String id = sc.nextLine().trim();  
            System.out.print("Enter Password: ");
            String p = sc.nextLine().trim();

            boolean ok = false;
            for (int i = 0; i < SALES_IDS.length; i++) {
                if (id.equalsIgnoreCase(SALES_IDS[i]) && p.equalsIgnoreCase(SALES_PASSES[i])) {
                    ok = true;
                    break;
                }
            }

            if (ok) {
                System.out.println("Sales login successful!");
                salesDashboard();
                loggedIn = true;
            } else {
                System.out.println("Invalid Sales ID or Password. Try again.");
                attempts++;
            }
        } catch (Exception e) {
            System.out.println("An error occurred. Please enter again.");
            sc.nextLine(); 
            attempts++;
        }
    }

    if (!loggedIn) {
        System.out.println("Maximum login attempts reached. Returning to main menu.");
    }
}

    // ---------- ADMIN DASHBOARD ----------
    static void adminDashboard() {
        while (true) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Vendors");
            System.out.println("3. Manage Orders");
            System.out.println("4. View Reports");
            System.out.println("5. Logout");
            System.out.print("Enter option: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1": manageProducts(); break;
                case "2": manageVendors(); break;
                case "3": manageOrders(); break;
                case "4": viewReports(); break;
                case "5": return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    // ---------- SALES DASHBOARD ----------
    static void salesDashboard() {
        while (true) {
            System.out.println("\n--- SALES DASHBOARD ---");
            System.out.println("1. Create Sales Order");
            System.out.println("2. View Products");
            System.out.println("3. Search Products");
            System.out.println("4. View Reports");
            System.out.println("5. Logout");
            System.out.print("Enter option: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1": createSalesOrder(); break;
                case "2": viewProducts(); break;
                case "3": searchProduct(); break;
                case "4": viewReports(); break;
                case "5": return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    // ---------- PRODUCT MANAGEMENT ----------
    static void manageProducts() {
        while (true) {
            System.out.println("\n--- MANAGE PRODUCTS ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("6. Back");
            System.out.print("Enter option: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1": addProduct(); break;
                case "2": viewProducts(); break;
                case "3": updateProduct(); break;
                case "4": deleteProduct(); break;
                case "5": searchProduct(); break;
                case "6": return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    static void addProduct() {
    if (productCount >= MAX_PRODUCTS) {
        System.out.println("Cannot add more products.");
        return;
    }

    // Product ID → digits only
    String id;
    while (true) {
        System.out.print("Enter Product ID (numbers only): ");
        id = sc.nextLine().trim();

        if (id.matches("\\d+")) {
            break;
        } else {
            System.out.println("Invalid ID! Product ID must contain numbers only.");
        }
    }

    // Product Name → alphabets only
    String name;
    while (true) {
        System.out.print("Enter Product Name (alphabets only): ");
        name = sc.nextLine().trim();

        if (name.matches("[a-zA-Z ]+")) {
            break;
        } else {
            System.out.println("Invalid Name! Product name cannot contain numbers.");
        }
    }

    // Stock → digits only
    String stock;
    while (true) {
        System.out.print("Enter Stock Quantity: ");
        stock = sc.nextLine().trim();

        if (stock.matches("\\d+")) {
            break;
        } else {
            System.out.println(" Invalid stock! Enter numbers only.");
        }
    }

    // Price → digits only
    String price;
    while (true) {
        System.out.print("Enter Product Price: ");
        price = sc.nextLine().trim();

        if (price.matches("\\d+")) {
            break;
        } else {
            System.out.println(" Invalid price! Enter numbers only.");
        }
    }

    // Store product
    productIDs[productCount] = id;
    productNames[productCount] = name;
    productStock[productCount] = stock;
    productPrice[productCount] = price;
    productCount++;

    saveProductsToFile();
    System.out.println(" Product added successfully.");
}


        productIDs[productCount] = id;
        productNames[productCount] = name;
        productStock[productCount] = stock;
        productPrice[productCount] = price;
        productCount++;
        saveProductsToFile();
        System.out.println("Product added.");
    }

    static void viewProducts() {
        System.out.println("\n--- PRODUCT LIST ---");
        if (productCount == 0) { System.out.println("No products available."); return; }
        System.out.printf("%-4s %-10s %-20s %-10s %-10s\n", "No", "ID", "Name", "Stock", "Price");
        for (int i = 0; i < productCount; i++) {
            System.out.printf("%-4d %-10s %-20s %-10s %-10s\n", i+1, productIDs[i], productNames[i], productStock[i], productPrice[i]);
        }
    }

    static void updateProduct() {
    viewProducts();
    System.out.print("Enter product number to update: ");
    String s = sc.nextLine();

    int idx = stringToIndex(s, productCount);
    if (idx == -1) return;

    // Validate Product Name (alphabets + spaces only)
    String newName;
    while (true) {
        System.out.print("Enter Product Name: ");
        newName = sc.nextLine().trim();

        if (newName.matches("[a-zA-Z ]+")) {
            break;
        } else {
            System.out.println(" Invalid name! Product name must contain only alphabets.");
        }
    }

    // Validate Stock (digits only)
    String newStock;
    while (true) {
        System.out.print("Enter new Stock Quantity: ");
        newStock = sc.nextLine().trim();

        if (newStock.matches("\\d+")) {
            break;
        } else {
            System.out.println("Invalid stock! Enter numbers only.");
        }
    }

    // Validate Price (digits only)
    String newPrice;
    while (true) {
        System.out.print("Enter new Product Price: ");
        newPrice = sc.nextLine().trim();

        if (newPrice.matches("\\d+")) {
            break;
        } else {
            System.out.println(" Invalid price! Enter numbers only.");
        }
    }

    // Update values
    productNames[idx] = newName;
    productStock[idx] = newStock;
    productPrice[idx] = newPrice;

    saveProductsToFile();
    System.out.println("Product updated successfully.");
}


    static void deleteProduct() {
        viewProducts();
        System.out.print("Enter product number to delete: "); String s = sc.nextLine();
        int idx = stringToIndex(s, productCount); if (idx==-1) return;
        for (int i=idx;i<productCount-1;i++) {
            productIDs[i] = productIDs[i+1];
            productNames[i] = productNames[i+1];
            productStock[i] = productStock[i+1];
            productPrice[i] = productPrice[i+1];
        }
        productCount--;
        saveProductsToFile();
        System.out.println("Product deleted.");
    }

    static void searchProduct() {
        System.out.print("Enter Product ID or Name: "); String q = sc.nextLine().toLowerCase();
        boolean found = false;
        for (int i=0;i<productCount;i++) {
            if (productIDs[i].toLowerCase().contains(q) || productNames[i].toLowerCase().contains(q)) {
                System.out.printf("%s | %s | Stock: %s | Price: %s\n", productNames[i], productIDs[i], productStock[i], productPrice[i]);
                found = true;
            }
        }
        if (!found) System.out.println("Product not found.");
    }

    // ---------- VENDOR MANAGEMENT ----------

static void manageVendors() {
    while (true) {
        System.out.println("\n--- MANAGE VENDORS ---");
        System.out.println("1. Add Vendor");
        System.out.println("2. View Vendors");
        System.out.println("3. Delete Vendor");
        System.out.println("4. Back");
        System.out.print("Enter option: ");
        String choice = sc.nextLine();

        switch (choice) {
            case "1": addVendor(); break;
            case "2": viewVendors(); break;
            case "3": deleteVendor(); break;
            case "4": return;
            default: System.out.println("Invalid option.");
        }
    }
}

static void addVendor() {
    if (vendorCount >= MAX_VENDORS) {
        System.out.println("Cannot add more vendors.");
        return;
    }

    String name;
    while (true) {
        System.out.print("Enter Vendor Name: ");
        name = sc.nextLine().trim();

        // force alphabet-only name
        if (name.matches("[a-zA-Z ]+")) {
            break;  // name valid
        } else {
            System.out.println(" Invalid name! Only alphabets and spaces allowed.");
        }
    }

    String contact;
    while (true) {
        System.out.print("Enter Vendor Contact (digits only): ");
        contact = sc.nextLine().trim();

        // force digits-only contact
        if (contact.matches("[0-9]+")) {
            break; // contact valid
        } else {
            System.out.println(" Invalid contact! Only numbers allowed.");
        }
    }

    // store safely
    vendorNames[vendorCount] = name;
    vendorContacts[vendorCount] = contact;
    vendorCount++;

    saveVendorsToFile();
    System.out.println("Vendor added successfully!");
}

static void viewVendors() {
    System.out.println("\n--- VENDORS ---");
    if (vendorCount == 0) {
        System.out.println("No vendors available.");
        return;
    }

    for (int i = 0; i < vendorCount; i++) {
        System.out.printf("%d. %s | Contact: %s\n",
                i + 1, vendorNames[i], vendorContacts[i]);
    }
}

static void deleteVendor() {
    viewVendors();
    if (vendorCount == 0) return;

    System.out.print("Enter vendor number to delete: ");
    String s = sc.nextLine();

    int idx = stringToIndex(s, vendorCount);
    if (idx == -1) return;

    for (int i = idx; i < vendorCount - 1; i++) {
        vendorNames[i] = vendorNames[i + 1];
        vendorContacts[i] = vendorContacts[i + 1];
    }

    vendorCount--;
    saveVendorsToFile();
    System.out.println("Vendor deleted successfully!");
}

    // ---------- ORDERS ----------
    static void manageOrders() {
        while (true) {
            System.out.println("\n--- MANAGE ORDERS ---");
            System.out.println("1. Create Purchase Order");
            System.out.println("2. Create Sales Order");
            System.out.println("3. Back");
            System.out.print("Enter option: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1": createPurchaseOrder(); break;
                case "2": createSalesOrder(); break;
                case "3": return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    static void createPurchaseOrder() {
    viewProducts();
    System.out.print("Select product number to add stock: ");
    int idx = stringToIndex(sc.nextLine(), productCount);
    if (idx == -1) return;

    System.out.print("Enter quantity to add: ");
    String qty = sc.nextLine();

    try {
        int current = Integer.parseInt(productStock[idx]);
        int add = Integer.parseInt(qty);

        if (add <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        productStock[idx] = String.valueOf(current + add);

        // store purchase record
        purchaseProducts[purchaseCount] = productNames[idx];
        purchaseQuantity[purchaseCount] = qty;
        purchaseCount++;

        saveProductsToFile();
        System.out.println(" Purchase order completed.");
    } catch (Exception e) {
        System.out.println("Invalid quantity.");
    }
}


    static void createSalesOrder() {
        viewProducts();
        System.out.print("Select product number to sell: ");
        int idx = stringToIndex(sc.nextLine(), productCount); if (idx==-1) return;
        System.out.print("Enter quantity to sell: ");
        String qty = sc.nextLine();
        try {
            int current = Integer.parseInt(productStock[idx]);
            int sell = Integer.parseInt(qty);
            if(sell<0 || sell>current){System.out.println("Invalid quantity."); return;}
            productStock[idx] = String.valueOf(current-sell);
            salesProducts[salesCount] = productNames[idx];
            salesQuantity[salesCount] = qty;
            salesCount++;
            saveProductsToFile();
            System.out.println("Sales order completed.");
        } catch(Exception e){System.out.println("Invalid quantity.");}
    }

    // ---------- REPORTS ----------
    static void viewReports() {
    while (true) {
        System.out.println("\n--- VIEW REPORTS MENU ---");
        System.out.println("1. View Stock Report");
        System.out.println("2. Low Stock Report");
        System.out.println("3. Sales Summary");
        System.out.println("4. Purchase Summary");
        System.out.println("5. Back to Dashboard");
        System.out.print("Enter option: ");
        String choice = sc.nextLine();

        switch (choice) {
            case "1": stockReport(); break;
            case "2": lowStockReport(); break;
            case "3": salesSummary(); break;
            case "4": purchaseSummary(); break;
            case "5": return;
            default: System.out.println("Invalid option."); 
        }
    }
}

static void stockReport() {
    System.out.println("\n--- STOCK REPORT ---");
    for (int i = 0; i < productCount; i++) {
        System.out.printf("%s | %s | Stock: %s | Price: %s\n",
                productNames[i], productIDs[i], productStock[i], productPrice[i]);
    }
}

static void lowStockReport() {
    System.out.println("\n--- LOW STOCK REPORT (Stock <= 5) ---");
    boolean found = false;
    for (int i = 0; i < productCount; i++) {
        int stock = Integer.parseInt(productStock[i]);
        if (stock <= 5) {
            System.out.printf("%s | %s | Stock: %s\n",
                    productNames[i], productIDs[i], productStock[i]);
            found = true;
        }
    }
    if (!found) System.out.println("No products with low stock.");
}

static void salesSummary() {
    System.out.println("\n--- SALES SUMMARY ---");
    if (salesCount == 0) {
        System.out.println("No sales yet.");
        return;
    }
    for (int i = 0; i < salesCount; i++) {
        System.out.printf("%s sold: %s\n", salesProducts[i], salesQuantity[i]);
    }
}

static void purchaseSummary() {
    System.out.println("\n--- PURCHASE SUMMARY ---");

    if (purchaseCount == 0) {
        System.out.println("No purchase orders recorded.");
        return;
    }

    for (int i = 0; i < purchaseCount; i++) {
        System.out.printf("%s purchased: %s units\n",
                purchaseProducts[i], purchaseQuantity[i]);
    }
}


    // ---------- FILE HANDLING ----------
    static void saveProductsToFile(){
        try(PrintWriter pw=new PrintWriter(new FileWriter(PRODUCT_FILE))){
            for(int i=0;i<productCount;i++){
                pw.println(productIDs[i]+","+productNames[i]+","+productStock[i]+","+productPrice[i]);
            }
        } catch(Exception e){System.out.println("Error saving products: "+e.getMessage());}
    }

    static void loadProductsFromFile(){
        File f = new File(PRODUCT_FILE);
        if(!f.exists()) return;
        try(Scanner fs=new Scanner(f)){
            productCount=0;
            while(fs.hasNextLine()){
                String[] parts = fs.nextLine().split(",");
                if(parts.length<4) continue;
                productIDs[productCount]=parts[0];
                productNames[productCount]=parts[1];
                productStock[productCount]=parts[2];
                productPrice[productCount]=parts[3];
                productCount++;
            }
        } catch(Exception e){System.out.println("Error loading products: "+e.getMessage());}
    }

    static void saveVendorsToFile(){
        try(PrintWriter pw=new PrintWriter(new FileWriter(VENDOR_FILE))){
            for(int i=0;i<vendorCount;i++){
                pw.println(vendorNames[i]+","+vendorContacts[i]);
            }
        } catch(Exception e){System.out.println("Error saving vendors: "+e.getMessage());}
    }

    static void loadVendorsFromFile(){
        File f = new File(VENDOR_FILE);
        if(!f.exists()) return;
        try(Scanner fs=new Scanner(f)){
            vendorCount=0;
            while(fs.hasNextLine()){
                String[] parts = fs.nextLine().split(",");
                if(parts.length<2) continue;
                vendorNames[vendorCount]=parts[0];
                vendorContacts[vendorCount]=parts[1];
                vendorCount++;
            }
        } catch(Exception e){System.out.println("Error loading vendors: "+e.getMessage());}
    }

    // ---------- HELPERS ----------
    static int stringToIndex(String s, int max){
        try {
            int idx = Integer.parseInt(s)-1;
            if(idx<0 || idx>=max){System.out.println("Invalid number."); return -1;}
            return idx;
        } catch(Exception e){System.out.println("Invalid input."); return -1;}
    }

    static void addDefaultProducts(){
        String[] defaults = { "Vacuum", "Refrigerator", "AC", "Microwave Oven", "TV" };
        for(int i=0;i<defaults.length;i++){
            productIDs[i] = "P"+(i+1);
            productNames[i] = defaults[i];
            productStock[i] = "10";
            productPrice[i] = "1000";
        }
        productCount = defaults.length;
    }
    // Helper: Only alphabets + spaces allowed
static boolean isValidName(String name) {
    return name.matches("[a-zA-Z ]+");
}

// Helper: Only digits allowed
static boolean isValidContact(String contact) {
    return contact.matches("\\d+");
}
}







