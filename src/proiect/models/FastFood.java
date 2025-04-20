package proiect.models;

public class FastFood extends Restaurant {
    private boolean hasDriveThru;

    public FastFood(String name, String cuisineType, String phone, String address) {
        super(name, cuisineType, phone, address);
        this.hasDriveThru = true;
    }

    public boolean hasDriveThru() { return hasDriveThru; }

    @Override
    public String toString() {
        return String.format("[FastFood] %s - Drive-Thru: %s", super.getName(), hasDriveThru ? "Da" : "Nu");
    }
}
