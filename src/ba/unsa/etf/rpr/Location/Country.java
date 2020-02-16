package ba.unsa.etf.rpr.Location;

import java.util.Objects;

public class Country {
    private int id;
    private String name;
    private Continent continent;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id == country.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
