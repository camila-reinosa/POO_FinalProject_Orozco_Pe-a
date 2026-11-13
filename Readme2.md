classDiagram
    class Restaurant {
        -String name
        -Menu menu
        -ArrayList~Table~ tables
        -ArrayList~Staff~ staffMembers
        -ArrayList~Order~ activeOrders
        -ArrayList~Order~ orderHistory
        +recommendBestSellingItem() MenuItem
        +createNewOrder(Table, Staff) Order
        +checkoutOrder(Order) Invoice
    }
    
    class Menu {
        -ArrayList~MenuItem~ items
        +addItem(MenuItem)
        +removeItem(String)
        +getActiveItems() ArrayList~MenuItem~
    }
    
    class MenuItem {
        <<abstract>>
        -String id
        -String name
        -double price
        -boolean isActive
        +getDetails()* String
        +updatePrice(double)
    }
    
    class FoodItem {
        -boolean isVegan
        -String allergyInfo
        +getDetails() String
    }
    
    class BeverageItem {
        -boolean isAlcoholic
        -int volumeMl
        +getDetails() String
    }
    
    class Table {
        -int tableNumber
        -int capacity
        -boolean isOccupied
        +occupy()
        +vacate()
    }
    
    class Staff {
        -String employeeId
        -String name
        -String role
    }
    
    class Order {
        -String orderId
        -Table table
        -Staff waiter
        -ArrayList~OrderItem~ items
        -String status
        -Invoice invoice
        +addItem(MenuItem, int)
        +calculateTotal() double
        +markAsPaid(Invoice)
        +cancelOrder()
    }
    
    class OrderItem {
        -MenuItem item
        -int quantity
        +getSubtotal() double
    }
    
    class Invoice {
        -String invoiceId
        -double subtotal
        -double taxAmount
        -double totalAmount
        -long timestamp
    }
    
    class ItemSalesStat {
        -MenuItem item
        -int quantitySold
        +incrementQuantity(int)
    }

    Restaurant "1" *-- "1" Menu
    Restaurant "1" *-- "*" Table
    Restaurant "1" *-- "*" Staff
    Restaurant "1" *-- "*" Order
    Menu "1" *-- "*" MenuItem
    Order "1" *-- "*" OrderItem
    Order "1" o-- "1" Table
    Order "1" o-- "1" Staff
    Order "1" o-- "0..1" Invoice
    OrderItem "*" o-- "1" MenuItem
    Restaurant "1" *-- "*" ItemSalesStat : calculates
    MenuItem <|-- FoodItem
    MenuItem <|-- BeverageItem
