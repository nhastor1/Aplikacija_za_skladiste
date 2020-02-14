package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Location.Country;
import ba.unsa.etf.rpr.Location.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CityDAO {
    private static CityDAO instance = null;
    private Connection conn;
    private ObservableList<City> listCities = FXCollections.observableArrayList();
    private PreparedStatement allCityQuery, getCityQueryFromID, addCityQuery, getCityIDQuery;
    private int freeID;

    private CityDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allCityQuery = conn.prepareStatement("SELECT * FROM City");
            getCityQueryFromID = conn.prepareStatement("SELECT * FROM City WHERE id=?");
            addCityQuery = conn.prepareStatement("INSERT INTO User VALUES(?,?,?)");
            getCityIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM City");
            ResultSet rs = getCityIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static CityDAO getInstance() {
        if (instance == null)
            instance = new CityDAO();
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

    public City getCityFromRS(ResultSet rs){
        City c = null;
        try {
            Country cont = CountryDAO.getInstance().getCountry(rs.getInt(3));
            c = new City(rs.getInt(1), rs.getString(2), cont);
        } catch (SQLException e) {
            //
        }
        return c;
    }

    public City getCity(int id){
        City c = null;
        try {
            getCityQueryFromID.setInt(1, id);
            ResultSet rs = getCityQueryFromID.executeQuery();
            c = getCityFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public ObservableList<City> getListCity() {
        return listCities;
    }

    public City addCity(City c) {
        try {
            addCityQuery.setInt(1, freeID);
            addCityQuery.setString(2, c.getName());
            addCityQuery.setInt(3, c.getCountry().getId());
            addCityQuery.executeUpdate();
            c.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistCities();
        return c;
    }

    private void refreshlistCities(){
        listCities.clear();
        listCities.addAll(getAllCities());
    }

    private ArrayList<City> getAllCities() {
        ArrayList<City> result = new ArrayList<>();
        try {
            ResultSet rs = allCityQuery.executeQuery();
            while (rs.next()) {
                result.add(getCityFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
