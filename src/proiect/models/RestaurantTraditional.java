package proiect.models;

public class RestaurantTraditional extends Restaurant{
    private boolean servesAlcohol;

    public RestaurantTraditional(String name, String cuisineType, String phone, String address) {
        super(name, cuisineType, phone, address);
        this.servesAlcohol = true;
    }

    public boolean servesAlcohol() { return servesAlcohol; }

    @Override
    public String toString() {
        return String.format("[Restaurant Traditional] %s - Serveste alcool: %s", 
               super.getName(), servesAlcohol ? "Da" : "Nu");
    }
}
