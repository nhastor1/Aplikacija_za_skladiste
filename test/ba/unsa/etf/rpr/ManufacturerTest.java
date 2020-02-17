package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.DAO.ManufacturerDAO;
import ba.unsa.etf.rpr.Person.LegalPerson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ManufacturerTest {
    @BeforeAll
    static void removeDatabase() {
        File dbfile = new File("database.db");
        dbfile.delete();
    }

    @Test
    void constructor() {
        Manufacturer c = new Manufacturer();
        assertEquals(0, c.getId());
        assertThrows(NullPointerException.class, c::getName);
        c = new Manufacturer(1, new LegalPerson(1, null, "kat"));
        assertEquals(1, c.getId());
        assertEquals("kat", c.getName());
    }

    @Test
    void geterAndSeter() {
        Manufacturer c = new Manufacturer(1, new LegalPerson(1, null, "name"));
        c.setId(2);
        c.setManufacturer(new LegalPerson(1, null, "dog"));

        assertEquals(2, c.getId());
        assertEquals("dog", c.getName());
    }

    @Test
    void DAO(){
        Manufacturer c1 = new Manufacturer(1, new LegalPerson(1, null, "name"));
        Manufacturer c2 = new Manufacturer(2, new LegalPerson(1, null, "name"));

        ManufacturerDAO.getInstance().addManufacturer(c1);
        ManufacturerDAO.getInstance().addManufacturer(c2);
        Manufacturer b1 = ManufacturerDAO.getInstance().getManufacturer(1);
        Manufacturer b2 = ManufacturerDAO.getInstance().getManufacturer(2);

        assertEquals(b1.getId(), c1.getId());
        assertThrows(NullPointerException.class, b1::getName);

        assertEquals(b2.getId(), c2.getId());
        assertThrows(NullPointerException.class, b2::getName);
    }
}