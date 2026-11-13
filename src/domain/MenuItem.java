package domain;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    
    private final String id;
    private String name;
    private double price;
    private boolean isActive;

    public MenuItem(final String id, final String name, final double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.isActive = true;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isActive() { return isActive; }

    public void setPrice(final double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }
    
    public void setActive(final boolean active) { 
        this.isActive = active; 
    }

    public abstract String getDetails();
}
