package ba.unsa.etf.rpr.User;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class KorisniciModel {
    private ObservableList<User> korisnici = FXCollections.observableArrayList();
    private SimpleObjectProperty<User> trenutniKorisnik = new SimpleObjectProperty<>();

    public KorisniciModel() {
    }

    public void napuni() {
        korisnici.add(new User("Vedran", "Ljubović", "vljubovic@etf.unsa.ba", "vedranlj", "test"));
        korisnici.add(new User("Amra", "Delić", "adelic@etf.unsa.ba", "amrad", "test"));
        korisnici.add(new User("Tarik", "Sijerčić", "tsijercic1@etf.unsa.ba", "tariks", "test"));
        korisnici.add(new User("Rijad", "Fejzić", "rfejzic1@etf.unsa.ba", "rijadf", "test"));
        trenutniKorisnik.set(null);
    }

    public ObservableList<User> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(ObservableList<User> korisnici) {
        this.korisnici = korisnici;
    }

    public User getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public SimpleObjectProperty<User> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(User trenutniUser) {
        this.trenutniKorisnik.set(trenutniUser);
    }

    public void setTrenutniKorisnik(int i) {
        this.trenutniKorisnik.set(korisnici.get(i));
    }
}
