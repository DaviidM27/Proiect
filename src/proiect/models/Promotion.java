package proiect.models;

import java.time.LocalDate;

public class Promotion {
    private String title;
    private String description;
    private double discountPercentage;
    private LocalDate validUntil;

    public Promotion(String title, String description, double discountPercentage, LocalDate validUntil) {
        this.title = title;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.validUntil = validUntil;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getDiscountPercentage() { return discountPercentage; }
    public LocalDate getValidUntil() { return validUntil; }

    public boolean isActive() {
        return LocalDate.now().isBefore(validUntil);
    }

    @Override
    public String toString() {
        return String.format("%s (%.1f%% reducere, valabil pana la %s): %s", 
                title, discountPercentage, validUntil, description);
    }
}
