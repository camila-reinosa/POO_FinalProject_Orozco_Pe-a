package domain;

public class ItemSalesStat {
    
    private final MenuItem item;
    private int quantitySold;

    public ItemSalesStat(final MenuItem item, final int quantitySold) {
        this.item = item;
        this.quantitySold = quantitySold;
    }

    public MenuItem getItem() { return item; }
    public int getQuantitySold() { return quantitySold; }
    
    public void incrementQuantity(final int amount) {
        this.quantitySold += amount;
    }
}
