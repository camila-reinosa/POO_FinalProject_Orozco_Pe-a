package ui;

import data.CsvExporter;
import data.PersistenceManager;
import domain.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleUI {
    
    private final Scanner scanner;
    private final PersistenceManager persistenceManager;
    private final CsvExporter csvExporter;
    private Restaurant restaurant;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.persistenceManager = new PersistenceManager();
        this.csvExporter = new CsvExporter();
    }

    public void start() {
        restaurant = persistenceManager.loadRestaurant();
        
        if (restaurant == null) {
            System.out.println("No previous data found. Initializing new system...");
            restaurant = new Restaurant("Gourmet Central");
            seedInitialData();
        } else {
            System.out.println("System state loaded successfully for " + restaurant.getName());
        }
        mainMenu();
    }

    private void seedInitialData() {
        restaurant.addTable(new Table(1, 2));
        restaurant.addTable(new Table(2, 4));
        restaurant.addStaff(new Staff("S01", "Alice", "Waiter"));
        restaurant.getMenu().addItem(new FoodItem("F1", "Steak", 25.50, false, "None"));
        restaurant.getMenu().addItem(new BeverageItem("B1", "Wine", 12.00, true, 200));
    }

    private void mainMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n=== " + restaurant.getName() + " - Order Management System ===");
            System.out.println("1. Create New Order");
            System.out.println("2. View Active Orders");
            System.out.println("3. View Menu");
            System.out.println("4. Algorithm: Recommend Best Selling Item");
            System.out.println("5. Export History to CSV");
            System.out.println("6. Save & Exit");
            System.out.print("Select an option: ");

            final String choice = scanner.nextLine();
            
            switch (choice) {
                case "1" -> createOrderMenu();
                case "2" -> viewActiveOrdersMenu();
                case "3" -> viewMenu();
                case "4" -> runRecommendationAlgorithm();
                case "5" -> exportData();
                case "6" -> {
                    persistenceManager.saveRestaurant(restaurant);
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void createOrderMenu() {
        final Table table = restaurant.findAvailableTable();
        if (table == null) {
            System.out.println("No available tables at the moment.");
            return;
        }

        if (restaurant.getStaffMembers().isEmpty()) {
            System.out.println("No staff available to take the order.");
            return;
        }

        final Staff waiter = restaurant.getStaffMembers().get(0); 
        final Order newOrder = restaurant.createOrder(table, waiter);
        
        System.out.println("Order " + newOrder.getOrderId() + " created for Table " + table.getTableNumber());
    }

    private void viewActiveOrdersMenu() {
        final ArrayList<Order> activeOrders = restaurant.getActiveOrders();
        
        if (activeOrders.isEmpty()) {
            System.out.println("No active orders.");
            return;
        }

        for (int i = 0; i < activeOrders.size(); i++) {
            System.out.println((i + 1) + ". " + activeOrders.get(i).getOrderId() 
                    + " - Table " + activeOrders.get(i).getTable().getTableNumber());
        }
        
        System.out.print("Select order index to checkout/cancel (0 to return): ");
        try {
            final int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < activeOrders.size()) {
                manageSpecificOrder(activeOrders.get(index));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void manageSpecificOrder(final Order order) {
        System.out.println("\nManaging Order: " + order.getOrderId());
        System.out.println("1. Add Item to Order");
        System.out.println("2. Pay Order (Checkout)");
        System.out.println("3. Cancel Order");
        System.out.print("Choose action: ");
        
        final String action = scanner.nextLine();
        
        if (action.equals("1")) {
            viewMenu();
            System.out.print("Enter Item ID: ");
            final String itemId = scanner.nextLine();
            final MenuItem item = restaurant.getMenu().findItemById(itemId);
            
            if (item != null) {
                System.out.print("Enter Quantity: ");
                final int qty = Integer.parseInt(scanner.nextLine()); 
                order.addItem(item, qty);
                System.out.println("Item added.");
            } else {
                System.out.println("Item not found.");
            }
        } else if (action.equals("2")) {
            restaurant.finalizeOrder(order, true);
            System.out.println("Order Paid. Total: $" + order.getInvoice().getTotalAmount());
        } else if (action.equals("3")) {
            restaurant.finalizeOrder(order, false);
            System.out.println("Order Canceled.");
        }
    }

    private void viewMenu() {
        System.out.println("\n--- Current Menu ---");
        for (MenuItem item : restaurant.getMenu().getActiveItems()) {
            System.out.println(item.getId() + ": " + item.getDetails()); 
        }
    }

    private void runRecommendationAlgorithm() {
        final MenuItem recommended = restaurant.recommendBestSellingItem();
        
        if (recommended == null) {
            System.out.println("Not enough historical data to make a recommendation.");
        } else {
            System.out.println("--- ALGORITHM RECOMMENDATION ---");
            System.out.println("Based on historical sales, you should promote: " + recommended.getName());
        }
    }

    private void exportData() {
        System.out.print("Enter filename (e.g., report.csv): ");
        final String filename = scanner.nextLine();
        csvExporter.exportOrdersToCsv(restaurant.getOrderHistory(), filename);
    }

    public static void main(String[] args) {
        new ConsoleUI().start();
    }
}
