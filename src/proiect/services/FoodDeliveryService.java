package proiect.services;

import proiect.models.*;
import proiect.exceptions.OrderException;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class FoodDeliveryService {
    private List<Restaurant> restaurants = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<DeliveryDriver> drivers = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private Map<Restaurant, List<Product>> menus = new HashMap<>();
    private Map<Restaurant, List<Promotion>> promotions = new HashMap<>();

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
        menus.put(restaurant, new ArrayList<>());
    }

    public void addProductToRestaurant(Restaurant restaurant, Product product) {
        if (!menus.containsKey(restaurant)) {
            throw new IllegalArgumentException("Restaurantul nu exista!");
        }
        menus.get(restaurant).add(product);
    }

    public void registerClient(Client client) {
        clients.add(client);
    }

    public void addDriver(DeliveryDriver driver) {
        drivers.add(driver);
    }

    public Order createOrder(Client client, Restaurant restaurant) throws OrderException {
        if (!clients.contains(client)) {
            throw new OrderException("Clientul nu este inregistrat!");
        }
        if (!restaurants.contains(restaurant)) {
            throw new OrderException("Restaurantul nu exista!");
        }
        if (menus.get(restaurant).isEmpty()) {
            throw new OrderException("Restaurantul nu are produse în meniu!");
        }

        Order order = new Order(client, restaurant, LocalDateTime.now());
        orders.add(order);
        return order;
    }

    public void addProductToOrder(Order order, Product product, int quantity) throws OrderException {
        if (!orders.contains(order)) {
            throw new OrderException("Comanda nu exista!");
        }
        if (!menus.get(order.getRestaurant()).contains(product)) {
            throw new OrderException("Produsul nu este in meniu!");
        }
        order.addItem(product, quantity);
    }

    public void addReview(Client client, Restaurant restaurant, int rating, String comment) throws OrderException {
        if (!clientHasOrderedFrom(client, restaurant)) {
            throw new OrderException("Clientul nu a comandat din acest restaurant!");
        }
        if (rating < 1 || rating > 5) {
            throw new OrderException("Rating invalid! Trebuie să fie intre 1-5 stele.");
        }
        reviews.add(new Review(client, restaurant, rating, comment));
    }

    private boolean clientHasOrderedFrom(Client client, Restaurant restaurant) {
        return orders.stream()
                   .anyMatch(o -> o.getClient().equals(client) && 
                                 o.getRestaurant().equals(restaurant));
    }

    public void addPromotion(Restaurant restaurant, Promotion promo) {
        promotions.computeIfAbsent(restaurant, k -> new ArrayList<>()).add(promo);
    }

    public List<Promotion> getActivePromotions(Restaurant restaurant) {
        return promotions.getOrDefault(restaurant, new ArrayList<>())
                         .stream()
                         .filter(Promotion::isActive)
                         .toList();
    }

    public List<Review> getReviewsForRestaurant(Restaurant restaurant) {
        return reviews.stream()
                   .filter(r -> r.getRestaurant().equals(restaurant))
                   .collect(Collectors.toList());
    }

    public List<Product> getRestaurantMenu(Restaurant restaurant) {
        return Collections.unmodifiableList(menus.getOrDefault(restaurant, new ArrayList<>()));
    }

    public List<Order> getClientOrders(Client client) {
        return orders.stream()
                   .filter(o -> o.getClient().equals(client))
                   .collect(Collectors.toList());
    }

    public List<Restaurant> getRestaurants() {
        return Collections.unmodifiableList(restaurants);
    }

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    public List<DeliveryDriver> getDrivers() {
        return Collections.unmodifiableList(drivers);
    }
}