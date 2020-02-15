package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Location.Continent;
import ba.unsa.etf.rpr.User.Administrator;
import ba.unsa.etf.rpr.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContinentDAO {
    private static ContinentDAO instance = null;
    private Connection conn;
    private ObservableList<Continent> listContinents = FXCollections.observableArrayList();
    private PreparedStatement allContinentQuery, getContinentQueryFromID;

    private ContinentDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allContinentQuery = conn.prepareStatement("SELECT * FROM Continent");
            getContinentQueryFromID = conn.prepareStatement("SELECT * FROM Continent WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshListContinents();
    }

    public static ContinentDAO getInstance() {
        if (instance == null)
            instance = new ContinentDAO();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public Continent getContinentFromRS(ResultSet rs){
        Continent c = null;
        try {
            c = new Continent(rs.getInt(1), rs.getString(2));
        } catch (SQLException e) {
            //
        }
        return c;
    }

    public Continent getContinent(int id){
        Continent c = null;
        try {
            getContinentQueryFromID.setInt(1, id);
            ResultSet rs = getContinentQueryFromID.executeQuery();
            c = getContinentFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public ObservableList<Continent> getListContinent() {
        return listContinents;
    }

    private void refreshListContinents(){
        listContinents.clear();
        listContinents.addAll(getAllContinents());
    }

    private ArrayList<Continent> getAllContinents() {
        ArrayList<Continent> result = new ArrayList<>();
        try {
            ResultSet rs = allContinentQuery.executeQuery();
            while (rs.next()) {
                result.add(getContinentFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
