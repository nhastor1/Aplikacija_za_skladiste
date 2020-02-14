package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Manufacturer;
import ba.unsa.etf.rpr.Manufacturer;
import ba.unsa.etf.rpr.User.Administrator;
import ba.unsa.etf.rpr.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class ManufacturerDAO {
    private static ManufacturerDAO instance = null;
    private Connection conn;
    private ObservableList<Manufacturer> listManufacturers = FXCollections.observableArrayList();
    private PreparedStatement allManufacturerQuery, getManufacturerQueryFromID, addManufacturerQuery, getManufacturerIDQuery;
    private int freeID;

    private ManufacturerDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allManufacturerQuery = conn.prepareStatement("SELECT * FROM Manufacturer");
            getManufacturerQueryFromID = conn.prepareStatement("SELECT * FROM Manufacturer WHERE id=?");
            addManufacturerQuery = conn.prepareStatement("INSERT INTO User VALUES(?,?,?)");
            getManufacturerIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Manufacturer");
            ResultSet rs = getManufacturerIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ManufacturerDAO getInstance() {
        if (instance == null)
            instance = new ManufacturerDAO();
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

    public Manufacturer getManufacturerFromRS(ResultSet rs){
        Manufacturer c = null;
//        try {
//            City cont = CityDAO.getInstance().getCity(rs.getInt(4));
//            c = new Manufacturer(rs.getInt(1), rs.getString(2), rs.getInt(3), cont);
//        } catch (SQLException e) {
//            //
//        }
        return c;
    }

    public Manufacturer getManufacturer(int id){
        Manufacturer c = null;
        try {
            getManufacturerQueryFromID.setInt(1, id);
            ResultSet rs = getManufacturerQueryFromID.executeQuery();
            c = getManufacturerFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public ObservableList<Manufacturer> getListManufacturer() {
        return listManufacturers;
    }

    public Manufacturer addManufacturer(Manufacturer c) {
//        try {
//            addManufacturerQuery.setInt(1, freeID);
//            addManufacturerQuery.setString(2, c.getStreet());
//            addManufacturerQuery.setInt(3, c.getNumber());
//            addManufacturerQuery.setInt(4, c.getCity().getId());
//            addManufacturerQuery.executeUpdate();
//            c.setId(freeID);
//            freeID++;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        refreshlistManufacturers();
        return c;
    }

    private void refreshlistManufacturers(){
        listManufacturers.clear();
        listManufacturers.addAll(getAllManufacturers());
    }

    private ArrayList<Manufacturer> getAllManufacturers() {
        ArrayList<Manufacturer> result = new ArrayList<>();
        try {
            ResultSet rs = allManufacturerQuery.executeQuery();
            while (rs.next()) {
                result.add(getManufacturerFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
