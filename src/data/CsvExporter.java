package data;

import domain.Order;
import domain.OrderItem;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CsvExporter {
    
    public void exportOrdersToCsv(final ArrayList<Order> orders, final String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("OrderID,Status,TableNumber,WaiterName,TotalItems,TotalAmount\n");

            for (Order order : orders) {
                int totalItems = 0;
                for (OrderItem oi : order.getItems()) {
                    totalItems += oi.getQuantity();
                }

                final double totalAmount = order.getInvoice() != null 
                        ? order.getInvoice().getTotalAmount() 
                        : 0.0;

                final String line = String.format("%s,%s,%d,%s,%d,%.2f\n",
                        order.getOrderId(),
                        order.getStatus(),
                        order.getTable().getTableNumber(),
                        order.getWaiter().getName(),
                        totalItems,
                        totalAmount
                    );
                writer.write(line);
            }
            System.out.println("Data successfully exported to " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
        }
    }
}
