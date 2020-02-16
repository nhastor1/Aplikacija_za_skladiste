package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Location.Location;


import java.sql.Date;
import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private double price;
    private int amount;
    private Warehouse warehouse;
    private int guarantee = 0;
    private double discount = 0;
    private Category category = new Category();
    private Manufacturer manufacturer = new Manufacturer();
    private Date lifetime;
    private Date dateOfProduction;
    private Location locationOfProduction;


    public Product() {
    }

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, double price, int amount, Warehouse warehouse) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.warehouse = warehouse;
    }

    public Product(int id, String name, double price, int amount, Warehouse warehouse, int guarantee, double discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.warehouse = warehouse;
        this.guarantee = guarantee;
        this.discount = discount;
    }

    public Product(int id, String name, double price, int amount, Warehouse warehouse, int guarantee, double discount, Category category, Manufacturer manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.warehouse = warehouse;
        this.guarantee = guarantee;
        this.discount = discount;
        this.category = category;
        this.manufacturer = manufacturer;
    }

    public Product(int id, String name, double price, int amount, Warehouse warehouse, int guarantee, double discount, Category category, Manufacturer manufacturer, Date lifetime, Date dateOfProduction, Location locationOfProduction) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.warehouse = warehouse;
        this.guarantee = guarantee;
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
        return guarantee;
    }

    public void setGuarantee(int guarantee) {
        this.guarantee = guarantee;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
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

    public Date getLifetime() {
        return lifetime;
    }

    public void setLifetime(Date lifetime) {
        this.lifetime = lifetime;
    }

    public Date getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(Date dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public Location getLocationOfProduction() {
        return locationOfProduction;
    }

    public void setLocationOfProduction(Location locationOfProduction) {
        this.locationOfProduction = locationOfProduction;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void addAmount(int number){
        amount+=number;
    }

    public void takeAmount(int number){
        amount-=number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }
}
