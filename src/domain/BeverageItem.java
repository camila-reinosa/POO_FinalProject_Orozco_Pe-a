package domain;

public class BeverageItem extends MenuItem {
    
    private final boolean isAlcoholic;
    private final int volumeMl;

    public BeverageItem(final String id, final String name, final double price, 
                        final boolean isAlcoholic, final int volumeMl) {
        super(id, name, price);
        this.isAlcoholic = isAlcoholic;
        this.volumeMl = volumeMl;
    }

    public boolean isAlcoholic() { return isAlcoholic; }
    public int getVolumeMl() { return volumeMl; }

    @Override
    public String getDetails() {
        return String.format("[BEVERAGE] %s - $%.2f | Alcoholic: %b | Vol: %dml", 
                getName(), getPrice(), isAlcoholic, volumeMl);
    }
}
