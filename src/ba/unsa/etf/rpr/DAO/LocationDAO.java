package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Location.City;
import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Location.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LocationDAO {
    private static LocationDAO instance = null;
    private Connection conn;
    private ObservableList<Location> listLocations = FXCollections.observableArrayList();
    private PreparedStatement allLocationQuery, getLocationQueryFromID, addLocationQuery, getLocationIDQuery;
    private int freeID;

    private LocationDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allLocationQuery = conn.prepareStatement("SELECT * FROM Location");
            getLocationQueryFromID = conn.prepareStatement("SELECT * FROM Location WHERE id=?");
            addLocationQuery = conn.prepareStatement("INSERT INTO Location VALUES(?,?,?,?)");
            getLocationIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Location");
            ResultSet rs = getLocationIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LocationDAO getInstance() {
        if (instance == null)
            instance = new LocationDAO();
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

    public Location getLocationFromRS(ResultSet rs){
        Location c = null;
        try {
            City cont = CityDAO.getInstance().getCity(rs.getInt(4));
            c = new Location(rs.getInt(1), rs.getString(2), rs.getInt(3), cont);
        } catch (SQLException e) {
            //
        }
        return c;
    }

    public Location getLocation(int id){
        Location c = null;
        try {
            getLocationQueryFromID.setInt(1, id);
            ResultSet rs = getLocationQueryFromID.executeQuery();
            c = getLocationFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public ObservableList<Location> getListLocation() {
        return listLocations;
    }

    public Location addLocation(Location c) {
        try {
            addLocationQuery.setInt(1, freeID);
            addLocationQuery.setString(2, c.getStreet());
            addLocationQuery.setInt(3, c.getNumber());
            addLocationQuery.setInt(4, c.getCity().getId());
            addLocationQuery.executeUpdate();
            c.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistLocations();
        return c;
    }

    private void refreshlistLocations(){
        listLocations.clear();
        listLocations.addAll(getAllLocations());
    }

    private ArrayList<Location> getAllLocations() {
        ArrayList<Location> result = new ArrayList<>();
        try {
            ResultSet rs = allLocationQuery.executeQuery();
            while (rs.next()) {
                result.add(getLocationFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
