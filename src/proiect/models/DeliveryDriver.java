package proiect.models;

public class DeliveryDriver {
    private String name;
    private String contact;
    private String vehicleNumber;

    public DeliveryDriver(String name, String contact, String vehicleNumber) {
        this.name = name;
        this.contact = contact;
        this.vehicleNumber = vehicleNumber;
    }

    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getVehicleNumber() { return vehicleNumber; }

    @Override
    public String toString() {
        return String.format("%s (%s - %s)", name, contact, vehicleNumber);
    }
}
