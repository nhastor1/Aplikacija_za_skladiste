package ba.unsa.etf.rpr.Location;

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
}
