package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Warehouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WarehouseDAO {
    private static WarehouseDAO instance = null;
    private Connection conn;
    private ObservableList<Warehouse> listWarehouses = FXCollections.observableArrayList();
    private PreparedStatement allWarehouseQuery, getWarehouseQueryFromID, addWarehouseQuery, getWarehouseIDQuery, getWarehouseQueryFromName,
            removeManufacturerQuery, canDeleteQuerryProduct;
    private int freeID;
    private Warehouse currentWarehouse;

    private WarehouseDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allWarehouseQuery = conn.prepareStatement("SELECT * FROM Warehouse");
            getWarehouseQueryFromID = conn.prepareStatement("SELECT * FROM Warehouse WHERE id=?");
            getWarehouseQueryFromName = conn.prepareStatement("SELECT * FROM Warehouse WHERE name=?");
            addWarehouseQuery = conn.prepareStatement("INSERT INTO Warehouse VALUES(?,?,?)");
            getWarehouseIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Warehouse");
            canDeleteQuerryProduct = conn.prepareStatement("SELECT COUNT(*) FROM Product WHERE Product.warehouse=?");
            removeManufacturerQuery = conn.prepareStatement("DELETE FROM Warehouse WHERE id=?");
            ResultSet rs = getWarehouseIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
            if(freeID<1)
                freeID = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistWarehouses();
    }

    public static WarehouseDAO getInstance() {
        if (instance == null)
            instance = new WarehouseDAO();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public Warehouse getWarehouseFromRS(ResultSet rs){
        Warehouse w = null;
        try {
            w = new Warehouse(rs.getInt(1), rs.getString(2),
                    LocationDAO.getInstance().getLocation(rs.getInt(3)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return w;
    }

    public Warehouse getWarehouse(int id){
        if(id==0)
            return null;
        Warehouse c = null;
        try {
            getWarehouseQueryFromID.setInt(1, id);
            ResultSet rs = getWarehouseQueryFromID.executeQuery();
            if(rs.next())
                c = getWarehouseFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public ObservableList<Warehouse> getListWarehouse() {
        return listWarehouses;
    }

    public Warehouse addWarehouse(Warehouse w) {
        Warehouse Warehouse = getWarehouse(w.getName());
        if(Warehouse!=null)
            return Warehouse;

        try {
            addWarehouseQuery.setInt(1, freeID);
            addWarehouseQuery.setString(2, w.getName());
            addWarehouseQuery.setInt(3, w.getLocation().getId());
            addWarehouseQuery.executeUpdate();
            w.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistWarehouses();
        return w;
    }

    public Warehouse getWarehouse(String s){
        Warehouse c = null;
        try {
            getWarehouseQueryFromName.setString(1, s);
            ResultSet rs = getWarehouseQueryFromName.executeQuery();
            if(rs.next()){
                c = getWarehouseFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public Warehouse getCurrentWarehouse() {
        return currentWarehouse;
    }

    public void setCurrentWarehouse(Warehouse currentWarehouse) {
        this.currentWarehouse = currentWarehouse;
    }

    public boolean removeWarehouse(Warehouse c){
        if(!canDelete(c.getId()))
            return false;
        try {
            removeManufacturerQuery.setInt(1, c.getId());
            removeManufacturerQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistWarehouses();
        return true;
    }

    private void refreshlistWarehouses(){
        listWarehouses.clear();
        listWarehouses.addAll(getAllWarehouses());
    }

    private ArrayList<Warehouse> getAllWarehouses() {
        ArrayList<Warehouse> result = new ArrayList<>();
        try {
            ResultSet rs = allWarehouseQuery.executeQuery();
            while (rs.next()) {
                result.add(getWarehouseFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private boolean canDelete(int id){
        try {
            canDeleteQuerryProduct.setInt(1, id);
            ResultSet rs = canDeleteQuerryProduct.executeQuery();
            if(rs.getInt(1)==0)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
