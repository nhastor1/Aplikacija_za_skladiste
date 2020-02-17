package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Language;
import ba.unsa.etf.rpr.User.Administrator;
import ba.unsa.etf.rpr.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDAO {
    private static UserDAO instance = null;
    private Connection conn;
    private ObservableList<User> listUsers = FXCollections.observableArrayList();
    private PreparedStatement allUserQuery, getUserQueryFromID, addUserQuery, removeUserQuery, updateUserQuery, getIDQuery,
            getUserQueryFromUsername, countUsernameQuery, getLanguageQuerry, setLanguageQuerry, deleteLanguageQuery;
    private int freeID;

    private UserDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:UserDatabase.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            allUserQuery = conn.prepareStatement("SELECT * FROM User");
        } catch (SQLException e) {
            createDatabase();
        }
        try {
            allUserQuery = conn.prepareStatement("SELECT * FROM User");
            addUserQuery = conn.prepareStatement("INSERT INTO User VALUES(?,?,?,?,?,?,?)");
            updateUserQuery = conn.prepareStatement("UPDATE User SET first_name=?, last_name=?, email=?, username=?, password=?, admin=? WHERE id=?");

            getUserQueryFromID = conn.prepareStatement("SELECT * FROM User WHERE id=?");
            getUserQueryFromUsername = conn.prepareStatement("SELECT * FROM User WHERE username=?");
            removeUserQuery = conn.prepareStatement("DELETE FROM User WHERE id=?");
            countUsernameQuery = conn.prepareStatement("SELECT Count(*) FROM User WHERE username=? AND id<>?");

            getIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM User");
            ResultSet rs = getIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);

            getLanguageQuerry = conn.prepareStatement("SELECT * FROM Language");
            deleteLanguageQuery = conn.prepareStatement("DELETE FROM Language WHERE lan IN (1,2)");
            setLanguageQuerry = conn.prepareStatement("INSERT INTO Language VALUES (?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshListUsers();
    }

    private void createDatabase() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("UserDatabase.sql"));
            String sqlQuery = "";
            while (input.hasNext()) {
                sqlQuery += input.nextLine();
                if ( sqlQuery.length() > 1 && sqlQuery.charAt( sqlQuery.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            } // ...
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("There is no sql file... continuing wiht empty database");
        }
    }

    public static UserDAO getInstance() {
        if (instance == null)
            instance = new UserDAO();
        return instance;
    }

    public static void removeInstance() {
        try {
            instance.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        instance = null;
    }

    public User getUserFromRS(ResultSet rs){
        User u = null;
        try {
            int admin = rs.getInt(7);

            if(admin==0)
                u = new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6));
            else
                u = new Administrator(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6));
        } catch (SQLException e) {
            //
        }
        return u;
    }

    public User addUser(User s) {
        if(isExsisting(s))
            throw new IllegalArgumentException("There is user with same username");
        try {
            addUserQuery.setInt(1, freeID);
            addUserQuery.setString(2, s.getFirstName());
            addUserQuery.setString(3, s.getLastName());
            addUserQuery.setString(4, s.getEmail());
            addUserQuery.setString(5, s.getUsername());
            addUserQuery.setString(6, s.getPassword());

            int admin;
            if(s instanceof Administrator)
                admin = 1;
            else
                admin = 0;
            addUserQuery.setInt(7, admin);

            addUserQuery.executeUpdate();
            s.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshListUsers();
        return s;
    }

    public User updateUser(User s) {
        if(isExsisting(s))
            throw new IllegalArgumentException("There is user with sam username");
        try {
            updateUserQuery.setString(1, s.getFirstName());
            updateUserQuery.setString(2, s.getLastName());
            updateUserQuery.setString(3, s.getEmail());
            updateUserQuery.setString(4, s.getUsername());
            updateUserQuery.setString(5, s.getPassword());

            int admin;
            if(s instanceof Administrator)
                admin = 1;
            else
                admin = 0;
            updateUserQuery.setInt(6, admin);
            updateUserQuery.setInt(7, s.getId());

            updateUserQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshListUsers();
        return s;
    }

    public void removeUser(User u){
        try {
            removeUserQuery.setInt(1, u.getId());
            removeUserQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshListUsers();
    }

    public User getUser(int id){
        User u = null;
        try {
            getUserQueryFromID.setInt(1, id);
            ResultSet rs = getUserQueryFromID.executeQuery();
            u = getUserFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public User getUser(String username){
        User u = null;
        try {
            getUserQueryFromUsername.setString(1, username);
            ResultSet rs = getUserQueryFromUsername.executeQuery();
            u = getUserFromRS(rs);

        } catch (SQLException e) {
            //
        }
        return u;
    }

    public ObservableList<User> getListUsers(User u) {
        if (u instanceof Administrator){
            return listUsers;
        }
        else{
            ObservableList<User> users = FXCollections.observableArrayList();
            users.add(u);
            return  users;
        }
    }

    public boolean isExsisting(String username, int id){
        try {
            countUsernameQuery.setString(1, username);
            countUsernameQuery.setInt(2, id);
            ResultSet rs = countUsernameQuery.executeQuery();
            if(rs.getInt(1)==0)
                return false;
        } catch (SQLException e) {
            //
        }
        return true;
    }

    public boolean isExsisting(User u){
        return isExsisting(u.getUsername(), u.getId());
    }

    public Language getLanguage(){
        Language lan =  Language.English;
        try {
            ResultSet rs = getLanguageQuerry.executeQuery();
            int i =  rs.getInt(1);
            if(i==2)
                lan = Language.Bosnian;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lan;
    }

    public void setLanguage(Language lan){
        try {
            deleteLanguageQuery.executeUpdate();
            int i = 1;
            if(lan.equals(Language.Bosnian))
                i = 2;
            setLanguageQuerry.setInt(1, i);
            setLanguageQuerry.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<User> getAllUsers() {
        ArrayList<User> result = new ArrayList<>();
        try {
            ResultSet rs = allUserQuery.executeQuery();
            while (rs.next()) {
                result.add(getUserFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void refreshListUsers(){
        listUsers.clear();
        listUsers.addAll(getAllUsers());
    }
}
