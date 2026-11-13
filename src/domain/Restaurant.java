package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {
    
    private static final double DEFAULT_TAX_RATE = 0.19; 

    private final String name;
    private final Menu menu;
    private final ArrayList<Table> tables;
    private final ArrayList<Staff> staffMembers;
    private final ArrayList<Order> activeOrders;
    private final ArrayList<Order> orderHistory; 

    public Restaurant(final String name) {
        this.name = name;
        this.menu = new Menu();
        this.tables = new ArrayList<>();
        this.staffMembers = new ArrayList<>();
        this.activeOrders = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
    }

    public String getName() { return name; }
    public Menu getMenu() { return menu; }
    public ArrayList<Table> getTables() { return tables; }
    public ArrayList<Staff> getStaffMembers() { return staffMembers; }
    public ArrayList<Order> getActiveOrders() { return activeOrders; }
    public ArrayList<Order> getOrderHistory() { return orderHistory; }

    public void addTable(final Table table) { tables.add(table); }
    public void addStaff(final Staff staff) { staffMembers.add(staff); }

    public Table findAvailableTable() {
        for (Table table : tables) {
            if (!table.isOccupied()) return table;
        }
        return null;
    }

    public Order createOrder(final Table table, final Staff waiter) {
        final String newOrderId = "ORD-" + (activeOrders.size() + orderHistory.size() + 1);
        final Order order = new Order(newOrderId, table, waiter);
        
        activeOrders.add(order);
        return order;
    }

    public void finalizeOrder(final Order order, final boolean isPaid) {
        if (!activeOrders.contains(order)) {
            throw new IllegalArgumentException("Order not found in active list.");
        }
        
        if (isPaid) {
            order.checkout(DEFAULT_TAX_RATE); 
        } else {
            order.cancel();
        }
        
        activeOrders.remove(order);
        orderHistory.add(order);
    }

    public MenuItem recommendBestSellingItem() {
        final ArrayList<ItemSalesStat> stats = new ArrayList<>();

        for (Order order : orderHistory) {
            if (order.getStatus().equals(Order.STATUS_PAID)) {
                for (OrderItem orderItem : order.getItems()) {
                    boolean found = false;
                    for (ItemSalesStat stat : stats) {
                        if (stat.getItem().getId().equals(orderItem.getItem().getId())) {
                            stat.incrementQuantity(orderItem.getQuantity());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        stats.add(new ItemSalesStat(orderItem.getItem(), orderItem.getQuantity()));
                    }
                }
            }
        }

        ItemSalesStat topStat = null;
        for (ItemSalesStat stat : stats) {
            if (topStat == null || stat.getQuantitySold() > topStat.getQuantitySold()) {
                topStat = stat;
            }
        }

        return topStat != null ? topStat.getItem() : null;
    }
}
