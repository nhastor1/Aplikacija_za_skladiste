package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Person.LegalPerson;

public class Manufacturer {
    int id;
    LegalPerson manufacturer;

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
}
