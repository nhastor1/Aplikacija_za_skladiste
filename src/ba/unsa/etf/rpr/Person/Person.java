package ba.unsa.etf.rpr.Person;

import ba.unsa.etf.rpr.Location.Location;

public class Person {
    private int id;
    private Location location;
    private String name;

    public Person() {
    }

    public Person(int id, Location location, String name) {
        this.id = id;
        this.location = location;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
