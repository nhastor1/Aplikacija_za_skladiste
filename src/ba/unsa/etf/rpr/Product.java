package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Location.Location;

import java.time.LocalDateTime;

public class Product {
    int id;
    String name;
    double price;
    int Guarantee = 0;
    int discount = 0;
    Category category = new Category();
    Manufacturer manufacturer = new Manufacturer();
    LocalDateTime lifetime;
    LocalDateTime dateOfProduction;
    Location locationOfProduction;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, double price, int guarantee, int discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        Guarantee = guarantee;
        this.discount = discount;
    }

    public Product(int id, String name, double price, int guarantee, int discount, Category category, Manufacturer manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        Guarantee = guarantee;
        this.discount = discount;
        this.category = category;
        this.manufacturer = manufacturer;
    }

    public Product(int id, String name, double price, int guarantee, int discount, Category category, Manufacturer manufacturer, LocalDateTime lifetime, LocalDateTime dateOfProduction, Location locationOfProduction) {
        this.id = id;
        this.name = name;
        this.price = price;
        Guarantee = guarantee;
        this.discount = discount;
        this.category = category;
        this.manufacturer = manufacturer;
        this.lifetime = lifetime;
        this.dateOfProduction = dateOfProduction;
        this.locationOfProduction = locationOfProduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price - discount*price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getGuarantee() {
        return Guarantee;
    }

    public void setGuarantee(int guarantee) {
        Guarantee = guarantee;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDateTime getLifetime() {
        return lifetime;
    }

    public void setLifetime(LocalDateTime lifetime) {
        this.lifetime = lifetime;
    }

    public LocalDateTime getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(LocalDateTime dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public Location getLocationOfProduction() {
        return locationOfProduction;
    }

    public void setLocationOfProduction(Location locationOfProduction) {
        this.locationOfProduction = locationOfProduction;
    }
}
