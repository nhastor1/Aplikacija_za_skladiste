package ba.unsa.etf.rpr.Person;

import ba.unsa.etf.rpr.Location.Location;

public class NaturalPerson extends Person {

    public NaturalPerson() {
    }

    public NaturalPerson(int id, Location location, String name) {
        super(id, location, name);
    }
}
