package ba.unsa.etf.rpr.Location;

public class Country {
    int id;
    String name;
    Continent continent;

    public Country(int id, String name, Continent continent) {
        this.id = id;
        this.name = name;
        this.continent = continent;
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

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    @Override
    public String toString() {
        return name;
    }
}
