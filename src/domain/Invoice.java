package domain;

import java.io.Serializable;

public class Invoice implements Serializable {
    
    private final String invoiceId;
    private final double subtotal;
    private final double taxAmount;
    private final double totalAmount;
    private final long timestamp;

    public Invoice(final String invoiceId, final double subtotal, final double taxRate) {
        this.invoiceId = invoiceId;
        this.subtotal = subtotal;
        this.taxAmount = subtotal * taxRate;
        this.totalAmount = this.subtotal + this.taxAmount;
        this.timestamp = System.currentTimeMillis();
    }

    public String getInvoiceId() { return invoiceId; }
    public double getSubtotal() { return subtotal; }
    public double getTaxAmount() { return taxAmount; }
    public double getTotalAmount() { return totalAmount; }
    public long getTimestamp() { return timestamp; }
}
