package proiect;

import proiect.models.*;
import proiect.services.FoodDeliveryService;
import proiect.exceptions.OrderException;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FoodDeliveryService service = new FoodDeliveryService();

    public static void main(String[] args) {
        while (true) {
            try {
                displayMainMenu();
                int choice = readIntInput("Alegeti o optiune: ");
                
                switch (choice) {
                    case 1 -> addRestaurant();
                    case 2 -> addProductToRestaurant();
                    case 3 -> registerClient();
                    case 4 -> addDriver();
                    case 5 -> createOrder();
                    case 6 -> displayRestaurantMenu();
                    case 7 -> displayClientOrders();
                    case 8 -> addReview();
                    case 9 -> displayRestaurantReviews();
                    case 10 -> displayPromotions();
                    case 11 -> addPromotion();
                    case 0 -> {
                        System.out.println("Aplicatia se inchide...");
                        System.exit(0);
                    }
                }
            } catch (OrderException e) {
                System.out.println("Eroare: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Eroare neașteptata: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== FOOD DELIVERY PLATFORM ===");
        System.out.println("1. Adaugare restaurant");
        System.out.println("2. Adaugare produs in meniu");
        System.out.println("3. Inregistrare client");
        System.out.println("4. Adaugare livrator");
        System.out.println("5. Creare comanda");
        System.out.println("6. Vizualizare meniu restaurant");
        System.out.println("7. Vizualizare comenzi client");
        System.out.println("8. Adaugare review");
        System.out.println("9. Vizualizare review-uri restaurant");
        System.out.println("10. Vizualizare promotii active");
        System.out.println("11. Adaugare promotie la restaurant");
        System.out.println("0. Iesire");
    }

    private static void addRestaurant() throws OrderException {
        System.out.println("\n--- ADAUGARE RESTAURANT ---");
        System.out.print("Tip (1-FastFood, 2-Traditional): ");
        int type = scanner.nextInt();
        scanner.nextLine();
        
        String name = readStringInput("Nume restaurant: ");
        String cuisine = readStringInput("Tip bucatarie: ");
        String phone = readStringInput("Telefon: ");
        String address = readStringInput("Adresa: ");

        Restaurant restaurant = (type == 1) ?
            new FastFood(name, cuisine, phone, address) :
            new RestaurantTraditional(name, cuisine, phone, address);
        
        service.addRestaurant(restaurant);
        System.out.println("Restaurant adaugat cu succes!");
    }

    private static void addProductToRestaurant() throws OrderException {
        System.out.println("\n--- ADAUGARE PRODUS ---");
        List<Restaurant> restaurants = service.getRestaurants();
        
        if (restaurants.isEmpty()) {
            throw new OrderException("Nu exista restaurante disponibile!");
        }
        
        displayItems("Restaurante", restaurants);
        int restaurantIndex = readIntInput("Selectati restaurantul: ") - 1;
        
        String name = readStringInput("Nume produs: ");
        double price = readDoubleInput("Pret: ");
        String category = readStringInput("Categorie: ");
        
        service.addProductToRestaurant(
            restaurants.get(restaurantIndex),
            new Product(name, price, category)
        );
        System.out.println("Produs adaugat cu succes!");
    }

    private static void registerClient() {
        System.out.println("\n--- INREGISTRARE CLIENT ---");
        String name = readStringInput("Nume complet: ");
        String email = readStringInput("Email: ");
        String address = readStringInput("Adresa: ");
        
        service.registerClient(new Client(name, email, address));
        System.out.println("Client inregistrat cu succes!");
    }

    private static void addDriver() {
        System.out.println("\n--- ADaUGARE LIVRATOR ---");
        String name = readStringInput("Nume livrator: ");
        String contact = readStringInput("Numar telefon: ");
        String vehicle = readStringInput("Numar inmatriculare: ");
        
        service.addDriver(new DeliveryDriver(name, contact, vehicle));
        System.out.println("Livrator adaugat cu succes!");
    }

    private static void createOrder() throws OrderException {
        System.out.println("\n--- CREARE COMANDA ---");
        Client client = selectClient();
        Restaurant restaurant = selectRestaurant();
        
        Order order = service.createOrder(client, restaurant);
        
        while (true) {
            List<Product> menu = service.getRestaurantMenu(restaurant);
            displayItems("Produse disponibile", menu);
            
            int choice = readIntInput("Selectati produs (0 pentru finalizare): ");
            if (choice == 0) break;
            
            int quantity = readIntInput("Cantitate: ");
            service.addProductToOrder(order, menu.get(choice - 1), quantity);
            System.out.println("Produs adaugat în comandă");
        }
        
        System.out.println("Comanda creata cu succes! ID: " + order.hashCode());
    }

    private static void displayRestaurantMenu() {
        System.out.println("\n--- VIZUALIZARE MENIU ---");
        Restaurant restaurant = selectRestaurant();
        List<Product> menu = service.getRestaurantMenu(restaurant);
        
        if (menu.isEmpty()) {
            System.out.println("Nu exista produse in meniu!");
            return;
        }
        
        System.out.println("Meniu " + restaurant.getName() + ":");
        menu.forEach(System.out::println);
    }

    private static void displayClientOrders() {
        System.out.println("\n--- VIZUALIZARE COMENZI ---");
        Client client = selectClient();
        List<Order> orders = service.getClientOrders(client);
        
        if (orders.isEmpty()) {
            System.out.println("Nu exista comenzi pentru acest client!");
            return;
        }
        
        System.out.println("Comenzi " + client.getName() + ":");
        orders.forEach(System.out::println);
    }

    private static void addReview() throws OrderException {
        System.out.println("\n--- ADAUGARE REVIEW ---");
        Client client = selectClient();
        Restaurant restaurant = selectRestaurant();
        
        int rating = readIntInput("Rating (1-5 stele): ");
        String comment = readStringInput("Comentariu: ");
        
        service.addReview(client, restaurant, rating, comment);
        System.out.println("Review adaugat cu succes!");
    }

    private static void displayRestaurantReviews() {
        System.out.println("\n--- VIZUALIZARE REVIEW-URI ---");
        Restaurant restaurant = selectRestaurant();
        List<Review> reviews = service.getReviewsForRestaurant(restaurant);
        
        if (reviews.isEmpty()) {
            System.out.println("Nu exista review-uri pentru acest restaurant!");
            return;
        }
        
        System.out.println("Review-uri pentru " + restaurant.getName() + ":");
        reviews.forEach(System.out::println);
    }

    private static void addPromotion() {
        System.out.println("\n--- ADAUGARE PROMOTIE ---");
        Restaurant restaurant = selectRestaurant();

        String title = readStringInput("Titlu promotie: ");
        String description = readStringInput("Descriere: ");
        double discount = readDoubleInput("Reducere (%): ");
        String dateStr = readStringInput("Valabil pana la (format yyyy-mm-dd): ");

        try {
            LocalDate validUntil = LocalDate.parse(dateStr);
            Promotion promo = new Promotion(title, description, discount, validUntil);
            service.addPromotion(restaurant, promo);
            System.out.println("Promotia a fost adaugata cu succes!");
        } catch (Exception e) {
            System.out.println("Data invalida! Foloseste formatul corect (ex: 2025-05-10).");
        }
    }

    private static void displayPromotions() {
        System.out.println("\n--- VIZUALIZARE PROMOTII ---");
        Restaurant restaurant = selectRestaurant();
        List<Promotion> activePromos = service.getActivePromotions(restaurant);

        if (activePromos.isEmpty()) {
            System.out.println("Nu sunt promotii active pentru acest restaurant.");
        } else {
            System.out.println("Promotii active pentru " + restaurant.getName() + ":");
            activePromos.forEach(System.out::println);
        }
    }


    private static Client selectClient() {
        List<Client> clients = service.getClients();
        displayItems("Clienti", clients);
        return clients.get(readIntInput("Selectati clientul: ") - 1);
    }

    private static Restaurant selectRestaurant() {
        List<Restaurant> restaurants = service.getRestaurants();
        displayItems("Restaurante", restaurants);
        return restaurants.get(readIntInput("Selectati restaurantul: ") - 1);
    }

    private static <T> void displayItems(String title, List<T> items) {
        if (items.isEmpty()) {
            throw new IllegalStateException("Nu exista " + title.toLowerCase() + " disponibili!");
        }
        
        System.out.println(title + ":");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }

    private static String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int readIntInput(String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextInt();
        } finally {
            scanner.nextLine();
        }
    }

    private static double readDoubleInput(String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextDouble();
        } finally {
            scanner.nextLine();
        }
    }
}