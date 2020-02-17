package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.DAO.ContinentDAO;
import ba.unsa.etf.rpr.DAO.ProductDAO;
import ba.unsa.etf.rpr.Location.City;
import ba.unsa.etf.rpr.Location.Country;
import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Person.LegalPerson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @BeforeAll
    static void removeDatabase() {
        File dbfile = new File("database.db");
        dbfile.delete();
    }

    @Test
    void constructor() {
        Product c = new Product();
        assertEquals(0, c.getId());
        assertNull(c.getName());
        c = new Product(1, "kat", 2.5);
        assertEquals(1, c.getId());
        assertEquals("kat", c.getName());
        assertEquals(2.5, c.getPrice());
    }

    @Test
    void geterAndSeter() {
        Product c = new Product(1, "kat", 2.5);
        c.setId(2);
        c.setName("dog");
        c.setPrice(3);

        assertEquals(2, c.getId());
        assertEquals("dog", c.getName());
        assertEquals(3, c.getPrice());
    }

    @Test
    void DAO(){
        Warehouse w = new Warehouse(1, "kat", null);
        Category c = new Category(1, "kat", null);
        Country country = new Country(1, "c", ContinentDAO.getInstance().getContinent(5));
        City city = new City(1, "c", country);
        Location l1 = new Location(1, "Zmaja od Bosne", 23, city);
        Manufacturer m = new Manufacturer();
        Product c1 = new Product(1, "kat", 2.5);
        Product c2 = new Product(2, "dog", 10);

        c1.setWarehouse(w);
        c1.setCategory(c);
        c1.setLocationOfProduction(l1);

        c2.setWarehouse(w);
        c2.setCategory(c);
        c2.setLocationOfProduction(l1);

        ProductDAO.getInstance().addProduct(c1);
        ProductDAO.getInstance().addProduct(c2);
        Product b1 = ProductDAO.getInstance().getProduct(1);
        Product b2 = ProductDAO.getInstance().getProduct(2);

        assertEquals(b1.getId(), c1.getId());
        assertEquals(b1.getName(), c1.getName());
        assertEquals(b1.getPrice(), c1.getPrice());

        assertEquals(b2.getId(), c2.getId());
        assertEquals(b2.getName(), c2.getName());
        assertEquals(b2.getPrice(), c2.getPrice());
    }
}