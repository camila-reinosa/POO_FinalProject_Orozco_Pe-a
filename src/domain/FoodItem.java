package domain;

public class FoodItem extends MenuItem {
    
    private final boolean isVegan;
    private final String allergyInfo;

    public FoodItem(final String id, final String name, final double price, 
                    final boolean isVegan, final String allergyInfo) {
        super(id, name, price);
        this.isVegan = isVegan;
        this.allergyInfo = allergyInfo;
    }

    public boolean isVegan() { return isVegan; }
    public String getAllergyInfo() { return allergyInfo; }

    @Override
    public String getDetails() {
        return String.format("[FOOD] %s - $%.2f | Vegan: %b | Allergies: %s", 
                getName(), getPrice(), isVegan, allergyInfo);
    }
}
