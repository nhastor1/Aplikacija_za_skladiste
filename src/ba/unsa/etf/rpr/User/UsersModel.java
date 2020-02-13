package ba.unsa.etf.rpr.User;

import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Exception.InvalidPasswordException;
import ba.unsa.etf.rpr.Exception.InvalidUsernameException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsersModel {
    private ObservableList<User> users = FXCollections.observableArrayList(); 
    private SimpleObjectProperty<User> currentUser = new SimpleObjectProperty<>();
    private UserDAO dao;
    private User user;

    public UsersModel(String username, String password) throws InvalidPasswordException, InvalidUsernameException {
        dao = UserDAO.getInstance();
        user = dao.getUser(username);
        if(user==null)
            throw new InvalidUsernameException("Wrong username");
        if(!user.getPassword().equals(password))
            throw new InvalidPasswordException("Wrong password");
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
        System.out.println(users.size());
        for(User u : users){
            System.out.println(u.getId());
            System.out.println(u.getFirstName());
            System.out.println(u.getLastName());
            System.out.println(u.getEmail());
            System.out.println(u.getUsername());
            System.out.println(u.getPassword());
            dao.updateUser(u);
    }}

    private void refreshUsers(){
        users = dao.getListUsers(user);
    }
}
