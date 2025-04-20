package proiect.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private Client client;
    private Restaurant restaurant;
    private LocalDateTime orderTime;
    private String status;
    private Map<Product, Integer> items = new HashMap<>();

    public Order(Client client, Restaurant restaurant, LocalDateTime orderTime) {
        this.client = client;
        this.restaurant = restaurant;
        this.orderTime = orderTime;
        this.status = "In curs de procesare";
    }

    public void addItem(Product product, int quantity) {
        items.put(product, quantity);
    }

    public double getTotalPrice() {
        return items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }

    public Client getClient() { return client; }
    public Restaurant getRestaurant() { return restaurant; }
    public LocalDateTime getOrderTime() { return orderTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Comanda #%d - %s - Total: %.2f RON - Status: %s", 
                hashCode(), orderTime, getTotalPrice(), status);
    }
}
