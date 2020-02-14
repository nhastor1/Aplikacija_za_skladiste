package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Person.LegalPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LegalPersonDAO {
    private static LegalPersonDAO instance = null;
    private Connection conn;
    private ObservableList<LegalPerson> listLegalPersons = FXCollections.observableArrayList();
    private PreparedStatement allLegalPersonQuery, getLegalPersonQueryFromID, addLegalPersonQuery, getLegalPersonIDQuery;
    private int freeID;

    private LegalPersonDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allLegalPersonQuery = conn.prepareStatement("SELECT * FROM Legal_person");
            getLegalPersonQueryFromID = conn.prepareStatement("SELECT * FROM Legal_person WHERE id=?");
            addLegalPersonQuery = conn.prepareStatement("INSERT INTO User VALUES(?,?,?)");
            getLegalPersonIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Legal_person");
            ResultSet rs = getLegalPersonIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LegalPersonDAO getInstance() {
        if (instance == null)
            instance = new LegalPersonDAO();
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

    public LegalPerson getLegalPersonFromRS(ResultSet rs){
        LegalPerson c = null;
        try {
            Location loc = LocationDAO.getInstance().getLocation(rs.getInt(2));
            c = new LegalPerson(rs.getInt(1), loc, rs.getString(3));
        } catch (SQLException e) {
            //
        }
        return c;
    }

    public LegalPerson getLegalPerson(int id){
        LegalPerson c = null;
        try {
            getLegalPersonQueryFromID.setInt(1, id);
            ResultSet rs = getLegalPersonQueryFromID.executeQuery();
            c = getLegalPersonFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public ObservableList<LegalPerson> getListLegalPerson() {
        return listLegalPersons;
    }

    public LegalPerson addLegalPerson(LegalPerson c) {
        try {
            addLegalPersonQuery.setInt(1, freeID);
            addLegalPersonQuery.setInt(2, c.getLocation().getId());
            addLegalPersonQuery.setString(3, c.getName());
            addLegalPersonQuery.executeUpdate();
            c.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistLegalPersons();
        return c;
    }

    private void refreshlistLegalPersons(){
        listLegalPersons.clear();
        listLegalPersons.addAll(getAllLegalPersons());
    }

    private ArrayList<LegalPerson> getAllLegalPersons() {
        ArrayList<LegalPerson> result = new ArrayList<>();
        try {
            ResultSet rs = allLegalPersonQuery.executeQuery();
            while (rs.next()) {
                result.add(getLegalPersonFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
