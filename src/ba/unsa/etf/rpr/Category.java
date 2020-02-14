package ba.unsa.etf.rpr;

public class Category {
    int id;
    String name;
    Category supercategory;

    public Category() {
        supercategory = new Category();
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
}
