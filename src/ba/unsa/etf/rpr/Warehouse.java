package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Person.NaturalPerson;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Warehouse {
    int id;
    String name;
    Location location;

    public Warehouse() {
    }

    public Warehouse(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return id == warehouse.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
