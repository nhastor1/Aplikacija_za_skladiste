package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Manufacturer;
import ba.unsa.etf.rpr.Manufacturer;
import ba.unsa.etf.rpr.Person.LegalPerson;
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
    private Manufacturer currentManufacturer = null;
    private PreparedStatement allManufacturerQuery, getManufacturerQueryFromID, addManufacturerQuery, getManufacturerIDQuery, removeManufacturerQuery;
    private int freeID;

    private ManufacturerDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allManufacturerQuery = conn.prepareStatement("SELECT * FROM Manufacturer");
            getManufacturerQueryFromID = conn.prepareStatement("SELECT * FROM Manufacturer WHERE id=?");
            addManufacturerQuery = conn.prepareStatement("INSERT INTO Manufacturer VALUES(?,?)");
            getManufacturerIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Manufacturer");
            removeManufacturerQuery = conn.prepareStatement("DELETE FROM Manufacturer WHERE id=?");
            ResultSet rs = getManufacturerIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
            if(freeID<1)
                freeID = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistManufacturers();
    }

    public static ManufacturerDAO getInstance() {
        if (instance == null)
            instance = new ManufacturerDAO();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public Manufacturer getManufacturerFromRS(ResultSet rs){
        Manufacturer m = null;
        try {
            LegalPerson lp = LegalPersonDAO.getInstance().getLegalPerson(rs.getInt(2));
            m = new Manufacturer(rs.getInt(1), lp);
        } catch (SQLException e) {
            //
        }
        return m;
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

    public Manufacturer addManufacturer(Manufacturer m) {
        try {
            addManufacturerQuery.setInt(1, freeID);
            addManufacturerQuery.setInt(2, m.getManufacturer().getId());
            addManufacturerQuery.executeUpdate();
            m.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistManufacturers();
        return m;
    }

    public Manufacturer getCurrentManufacturer() {
        return currentManufacturer;
    }

    public void setCurrentManufacturer(Manufacturer currentManufacturer) {
        this.currentManufacturer = currentManufacturer;
    }

    public void removeManufacturer(Manufacturer m){
        try {
            removeManufacturerQuery.setInt(1, m.getId());
            removeManufacturerQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistManufacturers();
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
