package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.DAO.ContinentDAO;
import ba.unsa.etf.rpr.DAO.WarehouseDAO;
import ba.unsa.etf.rpr.Location.City;
import ba.unsa.etf.rpr.Location.Country;
import ba.unsa.etf.rpr.Location.Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    @BeforeAll
    static void removeDatabase() {
        File dbfile = new File("database.db");
        dbfile.delete();
    }

    @Test
    void constructor() {
        Warehouse c = new Warehouse();
        assertNull(c.getLocation());
        assertNull(c.getName());
        c = new Warehouse(1, "kat", null);
        assertEquals(1, c.getId());
        assertEquals("kat", c.getName());
    }

    @Test
    void geterAndSeter() {
        Warehouse c = new Warehouse(1, "kat", null);
        c.setId(2);
        c.setName("dog");

        assertEquals(2, c.getId());
        assertEquals("dog", c.getName());
    }

    @Test
    void DAO(){
        Country country = new Country(1, "c", ContinentDAO.getInstance().getContinent(5));
        City city = new City(1, "c", country);
        Location l = new Location(1, "Zmaja od Bosne", 23, city);
        Warehouse c1 = new Warehouse(1, "kat", l);
        Warehouse c2 = new Warehouse(2, "dog", l);

        WarehouseDAO.getInstance().addWarehouse(c1);
        WarehouseDAO.getInstance().addWarehouse(c2);
        Warehouse b1 = WarehouseDAO.getInstance().getWarehouse(1);
        Warehouse b2 = WarehouseDAO.getInstance().getWarehouse(2);

        assertEquals(b1.getId(), c1.getId());
        assertEquals(b1.getName(), c1.getName());

        assertEquals(b2.getId(), c2.getId());
        assertEquals(b2.getName(), c2.getName());
    }

}