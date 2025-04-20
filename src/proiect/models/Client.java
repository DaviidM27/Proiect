package proiect.models;

public class Client {
    private String name;
    private String email;
    private String address;

    public Client(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, email);
    }
}
