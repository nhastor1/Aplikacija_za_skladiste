package ba.unsa.etf.rpr.Location;

import java.util.Objects;

public class Continent {
    int id;
    String name;

    public Continent(int id, String naziv) {
        this.id = id;
        this.name = naziv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return name;
    }

    public void setNaziv(String naziv) {
        this.name = naziv;
    }

    @Override
    public String toString() {
        return  name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Continent continent = (Continent) o;
        return id == continent.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
