package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Person.LegalPerson;
import javafx.beans.property.SimpleIntegerProperty;

public class Manufacturer {
//    SimpleIntegerProperty id;
//    LegalPerson manufacturer;
//
//    public Manufacturer(){
//        id = new SimpleIntegerProperty();
//        manufacturer = new LegalPerson();
//    }
//
//    public Manufacturer(int id, LegalPerson manufacturer) {
//        this.id = new SimpleIntegerProperty(id);
//        this.manufacturer = manufacturer;
//    }
//
//    public int getId() {
//        return id.get();
//    }
//
//    public SimpleIntegerProperty idProperty() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id.set(id);
//    }
//
//    public LegalPerson getManufacturer() {
//        return manufacturer;
//    }

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
