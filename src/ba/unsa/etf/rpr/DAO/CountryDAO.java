package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Location.Continent;
import ba.unsa.etf.rpr.Location.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountryDAO {
    private static CountryDAO instance = null;
    private Connection conn;
    private ObservableList<Country> listCountries = FXCollections.observableArrayList();
    private PreparedStatement allCountryQuery, getCountryQueryFromID, addCountryQuery, getCountryIDQuery, getCountryQueryFromName;
    private int freeID;

    private CountryDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allCountryQuery = conn.prepareStatement("SELECT * FROM Country");
            getCountryQueryFromID = conn.prepareStatement("SELECT * FROM Country WHERE id=?");
            getCountryQueryFromName = conn.prepareStatement("SELECT * FROM Country WHERE name=?");
            addCountryQuery = conn.prepareStatement("INSERT INTO Country VALUES(?,?,?)");
            getCountryIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Country");
            ResultSet rs = getCountryIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
            if(freeID<1)
                freeID = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistCountries();
    }

    public static CountryDAO getInstance() {
        if (instance == null)
            instance = new CountryDAO();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public Country getCountryFromRS(ResultSet rs){
        Country c = null;
        try {
            Continent cont = ContinentDAO.getInstance().getContinent(rs.getInt(3));
            c = new Country(rs.getInt(1), rs.getString(2), cont);
        } catch (SQLException e) {
            //
        }
        return c;
    }

    public Country getCountry(int id){
        Country c = null;
        try {
            getCountryQueryFromID.setInt(1, id);
            ResultSet rs = getCountryQueryFromID.executeQuery();
            c = getCountryFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public ObservableList<Country> getListCountry() {
        return listCountries;
    }

    public Country addCountry(Country c) {
        Country country = getCountry(c.getName());
        if(country!=null)
            return country;

        try {
            addCountryQuery.setInt(1, freeID);
            addCountryQuery.setString(2, c.getName());
            addCountryQuery.setInt(3, c.getContinent().getId());
            addCountryQuery.executeUpdate();
            c.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistCountries();
        return c;
    }

    public Country getCountry(String s){
        Country c = null;
        try {
            getCountryQueryFromName.setString(1, s);
            ResultSet rs = getCountryQueryFromName.executeQuery();
            if(rs.next()){
                c = getCountryFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    private void refreshlistCountries(){
        listCountries.clear();
        listCountries.addAll(getAllCountries());
    }

    private ArrayList<Country> getAllCountries() {
        ArrayList<Country> result = new ArrayList<>();
        try {
            ResultSet rs = allCountryQuery.executeQuery();
            while (rs.next()) {
                result.add(getCountryFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
