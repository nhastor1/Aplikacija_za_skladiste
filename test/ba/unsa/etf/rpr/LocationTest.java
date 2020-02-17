package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.DAO.ContinentDAO;
import ba.unsa.etf.rpr.DAO.CountryDAO;
import ba.unsa.etf.rpr.DAO.LocationDAO;
import ba.unsa.etf.rpr.Location.City;
import ba.unsa.etf.rpr.Location.Country;
import ba.unsa.etf.rpr.Location.Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    @BeforeAll
    static void removeDatabase() {
        File dbfile = new File("database.db");
        dbfile.delete();
    }

    @Test
    void constructor() {
        Location l = new Location(1, "Zmaja od Bosne", 23, null);
        assertEquals(1, l.getId());
        assertEquals("Zmaja od Bosne", l.getStreet());
        assertEquals(23, l.getNumber());
        assertNull(l.getCity());
    }

    @Test
    void geterAndSeter() {
        Location l = new Location(1, "Zmaja od Bosne", 23, null);
        l.setId(2);
        l.setStreet("Bosne od zmaja");
        l.setNumber(2);

        assertEquals(2, l.getId());
        assertEquals("Bosne od zmaja", l.getStreet());
        assertEquals(2, l.getNumber());
        assertNull(l.getCity());
    }

    @Test
    void DAO(){
        Country country = new Country(1, "c", ContinentDAO.getInstance().getContinent(5));
        City city = new City(1, "c", country);
        Location l1 = new Location(1, "Zmaja od Bosne", 23, city);
        Location l2 = new Location(2, "Bosne od zmaja", 2, city);
        LocationDAO.getInstance().addLocation(l1);
        LocationDAO.getInstance().addLocation(l2);
        Location b1 = LocationDAO.getInstance().getLocation(1);
        Location b2 = LocationDAO.getInstance().getLocation(2);

        assertEquals(b1.getId(), l1.getId());
        assertEquals(b1.getStreet(), l1.getStreet());
        assertEquals(b1.getNumber(), l1.getNumber());

        assertEquals(b2.getId(), l2.getId());
        assertEquals(b2.getStreet(), l2.getStreet());
        assertEquals(b2.getNumber(), l2.getNumber());
    }

}