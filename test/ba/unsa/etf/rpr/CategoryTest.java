package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.DAO.CategoryDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @BeforeAll
    static void removeDatabase() {
        File dbfile = new File("database.db");
        dbfile.delete();
    }

    @Test
    void constructor() {
        Category c = new Category();
        assertNull(c.getSupercategory());
        assertNull(c.getName());
        c = new Category(1, "kat", null);
        assertEquals(1, c.getId());
        assertEquals("kat", c.getName());
    }

    @Test
    void geterAndSeter() {
        Category c = new Category(1, "kat", null);
        c.setId(2);
        c.setName("dog");
        c.setSupercategory(c);

        assertEquals(2, c.getId());
        assertEquals("dog", c.getName());
        assertEquals(c, c.getSupercategory());
    }

    @Test
    void DAO(){
        Category c1 = new Category(1, "kat", null);
        Category c2 = new Category(2, "dog", c1);
        CategoryDAO.getInstance().addCategory(c1);
        CategoryDAO.getInstance().addCategory(c2);
        Category b1 = CategoryDAO.getInstance().getCategory(1);
        Category b2 = CategoryDAO.getInstance().getCategory(2);

        assertEquals(b1.getId(), c1.getId());
        assertEquals(b1.getName(), c1.getName());
        assertEquals(b1.getSupercategory(), b1.getSupercategory());

        assertEquals(b2.getId(), c2.getId());
        assertEquals(b2.getName(), c2.getName());
        assertEquals(b2.getSupercategory().getId(), b2.getSupercategory().getId());
        assertEquals(b2.getSupercategory().getName(), b2.getSupercategory().getName());
        assertEquals(b2.getSupercategory().getSupercategory(), b2.getSupercategory().getSupercategory());
    }
}