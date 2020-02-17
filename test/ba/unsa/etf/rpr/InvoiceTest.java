package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.DAO.ContinentDAO;
import ba.unsa.etf.rpr.DAO.InvoiceDAO;
import ba.unsa.etf.rpr.Location.City;
import ba.unsa.etf.rpr.Location.Country;
import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Person.LegalPerson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {
    @BeforeAll
    static void removeDatabase() {
        File dbfile = new File("database.db");
        dbfile.delete();
    }

    @Test
    void constructor() {
        Invoice c = new Invoice(1, null, 1.5, 0.15);
        assertEquals(1, c.getId());
        assertNull(c.getCustomer());
        assertEquals(1.5, c.getPrice());
        assertEquals(0.15, c.getDiscount());
    }

    @Test
    void geterAndSeter() {
        Invoice c = new Invoice(1, null, 1.5, 0.15);
        c.setId(2);
        c.setCustomer(new LegalPerson());
        c.setPrice(3);
        c.setDiscount(0.5);

        assertEquals(2, c.getId());
        assertEquals(3, c.getPrice());
        assertEquals(0.5, c.getDiscount());
    }

    @Test
    void DAO(){
        LegalPerson lp = new LegalPerson(1, null, "name");
        Invoice c1 = new Invoice(1, lp, 1.5, 0.15);
        Invoice c2 = new Invoice(2, lp, 20, 0.5);

        InvoiceDAO.getInstance().addInvoice(c1);
        InvoiceDAO.getInstance().addInvoice(c2);
        Invoice b1 = InvoiceDAO.getInstance().getInvoice(1);
        Invoice b2 = InvoiceDAO.getInstance().getInvoice(2);

        assertEquals(b1.getId(), c1.getId());
        assertEquals(b1.getPrice(), c1.getPrice());
        assertEquals(b1.getDiscount(), c1.getDiscount());

        assertEquals(b2.getId(), c2.getId());
        assertEquals(b2.getDiscount(), c2.getDiscount());
        assertEquals(b2.getPrice(), c2.getPrice());
    }
}