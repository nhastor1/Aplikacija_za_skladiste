package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Manufacturer;
import ba.unsa.etf.rpr.User.Administrator;
import ba.unsa.etf.rpr.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ManufacturerDAO {
    private static ManufacturerDAO instance = null;
    private Connection conn;
    private ObservableList<User> listManufacturers = FXCollections.observableArrayList();
    private PreparedStatement allManufacturerQuery, getManufacturerQueryFromID, addManufacturerQuery, removeManufacturerQuery, getManufacturerIDQuery;
    private int freeID;

    private ManufacturerDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allManufacturerQuery = conn.prepareStatement("SELECT * FROM Manufacturer");
            addManufacturerQuery = conn.prepareStatement("INSERT INTO Manufacturer VALUES(?,?,?,?,?,?,?)");
//            updateUserQuery = conn.prepareStatement("UPDATE Manufacturer SET first_name=?, last_name=?, email=?, username=?, password=?, admin=? WHERE id=?");
//
            removeManufacturerQuery = conn.prepareStatement("DELETE FROM Manufacturer WHERE id=?");
//            countUsernameQuery = conn.prepareStatement("SELECT Count(*) FROM Manufacturer WHERE username=? AND id<>?");
            getManufacturerQueryFromID = conn.prepareStatement("SELECT * FROM Manufacturer WHERE id=?");

            getManufacturerIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Manufacturer");
            ResultSet rs = getManufacturerIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //refreshListUsers();
    }

//    public Manufacturer getManufacturerFromRS(ResultSet rs){
//        Manufacturer u = null;
//        try {
//            Location l = getLocationFromRs()
//            u = new Manufacturer(rs.getInt(1), rs.getString(2), rs.getInt(3));
//        } catch (SQLException e) {
//            //
//        }
//        return u;
//    }

}
