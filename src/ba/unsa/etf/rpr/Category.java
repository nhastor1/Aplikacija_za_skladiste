package ba.unsa.etf.rpr;

import java.util.Objects;

public class Category {
    private int id;
    private String name;
    private Category supercategory;

    public Category() {
        supercategory = null;
    }

    public Category(int id, String name, Category supercategory) {
        this.id = id;
        this.name = name;
        this.supercategory = supercategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getSupercategory() {
        return supercategory;
    }

    public void setSupercategory(Category supercategory) {
        this.supercategory = supercategory;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
