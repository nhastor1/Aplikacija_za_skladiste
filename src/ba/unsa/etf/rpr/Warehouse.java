package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Person.NaturalPerson;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    int id;
    String name;
    Location location;
    NaturalPerson responsiblePerson;
    Map<Product, Integer> products = new HashMap<>();

    public Warehouse() {
    }

    public Warehouse(int id, String name, Location location, NaturalPerson responsiblePerson, Map<Product, Integer> products) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.responsiblePerson = responsiblePerson;
        this.products = products;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public NaturalPerson getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(NaturalPerson responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public void addProuct(Product p, int number){
        if(products.containsKey(p))
            products.replace(p, products.get(p)+number);
        else
            products.put(p, number);
    }

    public int howMuch(Product p){
        Integer n = products.get(p);
        if(n==null)
            n = 0;
        return n;
    }

    public boolean take(Product p, int number){
        if(howMuch(p)<number)
            return false;
        products.replace(p, products.get(p) - number);
        return true;
    }
}
