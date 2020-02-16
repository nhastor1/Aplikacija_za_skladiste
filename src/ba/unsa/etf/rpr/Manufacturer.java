package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Person.LegalPerson;

import java.util.Objects;

public class Manufacturer {
    private int id;
    private LegalPerson manufacturer;

    public Manufacturer() {
    }

    public Manufacturer(int id, LegalPerson manufacturer) {
        this.id = id;
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LegalPerson getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(LegalPerson manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName(){
        return manufacturer.getName();
    }

    public String getAddres(){
        return manufacturer.getLocation() + "";
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
