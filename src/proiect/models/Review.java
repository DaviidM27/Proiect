package proiect.models;

public class Review {
    private final Client client;
    private final Restaurant restaurant;
    private final int rating;
    private final String comment;

    public Review(Client client, Restaurant restaurant, int rating, String comment) {
        this.client = client;
        this.restaurant = restaurant;
        this.rating = rating;
        this.comment = comment;
    }

    public Client getClient() {
        return client;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return String.format("★".repeat(rating) + "☆".repeat(5 - rating) + 
               " | " + client.getName() + " | " + comment);
    }
}