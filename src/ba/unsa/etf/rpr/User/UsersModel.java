package ba.unsa.etf.rpr.User;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsersModel {
    private ObservableList<User> users = FXCollections.observableArrayList(); 
    private SimpleObjectProperty<User> currentUser = new SimpleObjectProperty<>();

    public UsersModel() {
    }

    public void napuni() {
        users.add(new User("Vedran", "Ljubović", "vljubovic@etf.unsa.ba", "vedranlj", "test"));
        users.add(new User("Amra", "Delić", "adelic@etf.unsa.ba", "amrad", "test"));
        users.add(new User("Tarik", "Sijerčić", "tsijercic1@etf.unsa.ba", "tariks", "test"));
        users.add(new User("Rijad", "Fejzić", "rfejzic1@etf.unsa.ba", "rijadf", "test"));
        currentUser.set(null);
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public SimpleObjectProperty<User> currentUserProperty() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser.set(currentUser);
    }

    public void setCurrentUser(int i) {
        this.currentUser.set(users.get(i));
    }
}
