package ba.unsa.etf.rpr.User;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    int id;
    private SimpleStringProperty firstName, lastName, email, username, password;

    public User(int id, String fristName, String lastName, String email, String username, String password) {
        this.id = id;
        this.firstName = new SimpleStringProperty(fristName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    @Override
    public String toString() {
        return lastName.get() + " " + firstName.get();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String ime) {
        this.firstName.set(ime);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String prezime) {
        this.lastName.set(prezime);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public boolean checkPassword(){
        String s = password.getValue();
        boolean capital = false, lowercase = false, number = false;

        char c;
        for(int i=0; i<s.length(); i++){
            c = s.charAt(i);
            if(Character.isLowerCase(c))
                lowercase = true;
            else if(Character.isUpperCase(c))
                capital = true;
            else if(Character.isDigit(c))
                number = true;
        }

        if(capital && lowercase && number)
            return true;

        return false;
    }
}
