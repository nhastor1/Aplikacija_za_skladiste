package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Person.NaturalPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NaturalPersonDAO {
    private static NaturalPersonDAO instance = null;
    private Connection conn;
    private ObservableList<NaturalPerson> listNaturalPersons = FXCollections.observableArrayList();
    private PreparedStatement allNaturalPersonQuery, getNaturalPersonQueryFromID, addNaturalPersonQuery, getNaturalPersonIDQuery;
    private int freeID;

    private NaturalPersonDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allNaturalPersonQuery = conn.prepareStatement("SELECT * FROM Natural_person");
            getNaturalPersonQueryFromID = conn.prepareStatement("SELECT * FROM Natural_person WHERE id=?");
            addNaturalPersonQuery = conn.prepareStatement("INSERT INTO Natural_person VALUES(?,?,?)");
            getNaturalPersonIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Natural_person");
            ResultSet rs = getNaturalPersonIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
            if(freeID<1)
                freeID = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistNaturalPersons();
    }

    public static NaturalPersonDAO getInstance() {
        if (instance == null)
            instance = new NaturalPersonDAO();
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

    public NaturalPerson getNaturalPersonFromRS(ResultSet rs){
        NaturalPerson c = null;
        try {
            Location loc = LocationDAO.getInstance().getLocation(rs.getInt(2));
            c = new NaturalPerson(rs.getInt(1), loc, rs.getString(3));
        } catch (SQLException e) {
            //
        }
        return c;
    }

    public NaturalPerson getNaturalPerson(int id){
        NaturalPerson c = null;
        try {
            getNaturalPersonQueryFromID.setInt(1, id);
            ResultSet rs = getNaturalPersonQueryFromID.executeQuery();
            c = getNaturalPersonFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public ObservableList<NaturalPerson> getListNaturalPerson() {
        return listNaturalPersons;
    }

    public NaturalPerson addNaturalPerson(NaturalPerson c) {
        try {
            addNaturalPersonQuery.setInt(1, freeID);
            addNaturalPersonQuery.setInt(2, c.getLocation().getId());
            addNaturalPersonQuery.setString(3, c.getName());
            addNaturalPersonQuery.executeUpdate();
            c.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistNaturalPersons();
        return c;
    }

    private void refreshlistNaturalPersons(){
        listNaturalPersons.clear();
        listNaturalPersons.addAll(getAllNaturalPersons());
    }

    private ArrayList<NaturalPerson> getAllNaturalPersons() {
        ArrayList<NaturalPerson> result = new ArrayList<>();
        try {
            ResultSet rs = allNaturalPersonQuery.executeQuery();
            while (rs.next()) {
                result.add(getNaturalPersonFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
