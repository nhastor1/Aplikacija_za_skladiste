package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.DAO.ContinentDAO;
import ba.unsa.etf.rpr.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAO.ProductOrderDAO;
import ba.unsa.etf.rpr.Location.City;
import ba.unsa.etf.rpr.Location.Country;
import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Person.LegalPerson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ProductOrderTest {
    @BeforeAll
    static void removeDatabase() {
        File dbfile = new File("database.db");
        dbfile.delete();
    }

    @Test
    void constructor() {
        ProductOrder c = new ProductOrder(1, null, 2, null, 0.15);
        assertEquals(1, c.getId());
        assertNull(c.getProduct());
        assertEquals(2, c.getAmount());
        assertNull(c.getInvoice());
        assertEquals(0.15, c.getDiscount());
    }

    @Test
    void geterAndSeter() {
        ProductOrder c = new ProductOrder(1, null, 2, null, 0.15);
        c.setId(2);
        c.setAmount(5);
        c.setDiscount(0.3);

        assertEquals(2, c.getId());
        assertEquals(5, c.getAmount());
        assertEquals(0.3, c.getDiscount());
    }

    @Test
    void DAO(){
        Invoice i = new Invoice(1, null, 1.5, 0.15);
        Warehouse w = new Warehouse(1, "kat", null);
        Category c = new Category(1, "kat", null);
        Country country = new Country(1, "c", ContinentDAO.getInstance().getContinent(5));
        City city = new City(1, "c", country);
        Location l1 = new Location(1, "Zmaja od Bosne", 23, city);
        Manufacturer m = new Manufacturer();
        Product p = new Product(1, "kat", 2.5);

        p.setWarehouse(w);
        p.setCategory(c);
        p.setLocationOfProduction(l1);

        ProductOrder c1 = new ProductOrder(1, p, 2, i, 0.15);
        ProductOrder c2 = new ProductOrder(1, p, 3, i, 0.3);

        ProductOrderDAO.getInstance().addProductOrder(c1);
        ProductOrderDAO.getInstance().addProductOrder(c2);
        ProductDAO.getInstance().addProduct(p);
        ProductOrder b1 = ProductOrderDAO.getInstance().getProductOrder(1);
        ProductOrder b2 = ProductOrderDAO.getInstance().getProductOrder(2);

        assertEquals(b1.getId(), c1.getId());
        assertEquals(b1.getPrice(), c1.getPrice());
        assertEquals(b1.getDiscount(), c1.getDiscount());
        assertEquals(b1.getAmount(), c1.getAmount());

        assertEquals(b2.getId(), c2.getId());
        assertEquals(b2.getDiscount(), c2.getDiscount());
        assertEquals(b2.getPrice(), c2.getPrice());
        assertEquals(b2.getAmount(), c2.getAmount());
    }
}