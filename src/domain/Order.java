package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_PAID = "PAID";
    public static final String STATUS_CANCELED = "CANCELED";

    private final String orderId;
    private final Table table;
    private final Staff waiter;
    private final ArrayList<OrderItem> items;
    
    private String status;
    private Invoice invoice;

    public Order(final String orderId, final Table table, final Staff waiter) {
        this.orderId = orderId;
        this.table = table;
        this.waiter = waiter;
        this.items = new ArrayList<>();
        this.status = STATUS_ACTIVE;
        
        this.table.occupy(); 
    }

    public String getOrderId() { return orderId; }
    public Table getTable() { return table; }
    public Staff getWaiter() { return waiter; }
    public ArrayList<OrderItem> getItems() { return items; }
    public String getStatus() { return status; }
    public Invoice getInvoice() { return invoice; }

    public void addItem(final MenuItem item, final int quantity) {
        if (!status.equals(STATUS_ACTIVE)) {
            throw new IllegalStateException("Can only modify ACTIVE orders.");
        }
        
        for (OrderItem orderItem : items) {
            if (orderItem.getItem().getId().equals(item.getId())) {
                orderItem.addQuantity(quantity);
                return;
            }
        }
        items.add(new OrderItem(item, quantity));
    }

    public double calculateSubtotal() {
        double subtotal = 0.0;
        for (OrderItem item : items) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    public void checkout(final double taxRate) {
        if (!status.equals(STATUS_ACTIVE)) {
            throw new IllegalStateException("Order is not ACTIVE.");
        }
        
        this.invoice = new Invoice("INV-" + orderId, calculateSubtotal(), taxRate);
        this.status = STATUS_PAID;
        this.table.vacate();
    }

    public void cancel() {
        if (!status.equals(STATUS_ACTIVE)) {
            throw new IllegalStateException("Only ACTIVE orders can be canceled.");
        }
        
        this.status = STATUS_CANCELED;
        this.table.vacate();
    }
}
