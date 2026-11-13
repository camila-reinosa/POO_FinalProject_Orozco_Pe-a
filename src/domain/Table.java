package domain;

import java.io.Serializable;

public class Table implements Serializable {
    
    private final int tableNumber;
    private final int capacity;
    private boolean isOccupied;

    public Table(final int tableNumber, final int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.isOccupied = false;
    }

    public int getTableNumber() { return tableNumber; }
    public int getCapacity() { return capacity; }
    public boolean isOccupied() { return isOccupied; }

    public void occupy() {
        if (this.isOccupied) {
            throw new IllegalStateException("Table is already occupied.");
        }
        this.isOccupied = true;
    }

    public void vacate() {
        this.isOccupied = false;
    }
}
