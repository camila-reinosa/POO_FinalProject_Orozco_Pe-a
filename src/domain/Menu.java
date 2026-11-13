package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements Serializable {
    
    private final ArrayList<MenuItem> items;

    public Menu() {
        this.items = new ArrayList<>();
    }

    public void addItem(final MenuItem item) {
        this.items.add(item);
    }

    public ArrayList<MenuItem> getActiveItems() {
        final ArrayList<MenuItem> activeItems = new ArrayList<>();
        
        for (MenuItem item : items) {
            if (item.isActive()) {
                activeItems.add(item);
            }
        }
        return activeItems;
    }

    public MenuItem findItemById(final String id) {
        for (MenuItem item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
}
