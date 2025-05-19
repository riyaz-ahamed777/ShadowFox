import java.time.LocalDate;

public class InventoryItem {
    private String name;
    private int quantity;
    private double price;
    private String category;
    private LocalDate expirationDate;

    public InventoryItem(String name, int quantity, double price, String category, LocalDate expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.expirationDate = expirationDate;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate;}
}