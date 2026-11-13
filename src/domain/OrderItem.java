package domain;

import java.io.Serializable;

public class OrderItem implements Serializable {
    
    private final MenuItem item;
    private int quantity;

    public OrderItem(final MenuItem item, final int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() { return item; }
    public int getQuantity() { return quantity; }

    public void addQuantity(final int extraQuantity) {
        if (extraQuantity <= 0) {
            throw new IllegalArgumentException("Extra quantity must be positive.");
        }
        this.quantity += extraQuantity;
    }

    public double getSubtotal() {
        return item.getPrice() * quantity;
    }
}
