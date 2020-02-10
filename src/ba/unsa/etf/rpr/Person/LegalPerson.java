package ba.unsa.etf.rpr.Person;

import ba.unsa.etf.rpr.Location.Location;

public class LegalPerson extends Person {

    public LegalPerson() {
    }

    public LegalPerson(int id, Location location, String name) {
        super(id, location, name);
    }
}
