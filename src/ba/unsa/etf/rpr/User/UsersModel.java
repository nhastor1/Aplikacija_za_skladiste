package ba.unsa.etf.rpr.User;

import ba.unsa.etf.rpr.DAO.UserDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsersModel {
    private ObservableList<User> users = FXCollections.observableArrayList(); 
    private SimpleObjectProperty<User> currentUser = new SimpleObjectProperty<>();
    private UserDAO dao;
    private User user = new Administrator(1, "a", "a", "a", "a", "a");

    public UsersModel() {
        dao = UserDAO.getInstance();
    }

    public void napuni() {
        users = dao.getListUsers(user);
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

    public User addUser(User u){
        User newUser = dao.addUser(u);
        refreshUsers();
        return newUser;
    }

    public void removeUser(User u){
        dao.removeUser(u);
        refreshUsers();
    }

    public void updateAll(){
        for(User u : users)
            dao.updateUser(u);
    }

    private void refreshUsers(){
        users = dao.getListUsers(user);
    }
}
