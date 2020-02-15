package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Person.NaturalPerson;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    int id;
    String name;
    Location location;
//    NaturalPerson responsiblePerson;
    Map<Integer, Product> products = new HashMap<>();

    public Warehouse() {
    }

    public Warehouse(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Warehouse(int id, String name, Location location, Map<Integer, Product> products) {
        this.id = id;
        this.name = name;
        this.location = location;
//        this.responsiblePerson = responsiblePerson;
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

//    public NaturalPerson getResponsiblePerson() {
//        return responsiblePerson;
//    }
//
//    public void setResponsiblePerson(NaturalPerson responsiblePerson) {
//        this.responsiblePerson = responsiblePerson;
//    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Product> products) {
        this.products = products;
    }

    public boolean addProuct(Product p, int number){
        if(number<=0)
            return false;
        if(products.containsKey(p.getId()))
            products.get(p.getId()).addAmount(number);
        else
            products.put(p.getId(), p);

        return true;
    }

    public int howMuch(Product p){
        Product p2 = products.get(p.getId());
        if(p2==null)
            return 0;
        return p2.getAmount();
    }

    public boolean take(Product p, int number){
        if(howMuch(p)<number)
            return false;
        products.get(p.getId()).takeAmount(number);
        return true;
    }
}
