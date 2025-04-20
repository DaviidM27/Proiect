package proiect.models;

public class Restaurant {
    private String name;
    private String cuisineType;
    private String phone;
    private String address;

    public Restaurant(String name, String cuisineType, String phone, String address) {
        this.name = name;
        this.cuisineType = cuisineType;
        this.phone = phone;
        this.address = address;
    }

    public String getName() { return name; }
    public String getCuisineType() { return cuisineType; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", name, cuisineType, address);
    }
}
