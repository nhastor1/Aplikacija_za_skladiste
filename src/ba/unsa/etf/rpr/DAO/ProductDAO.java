package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductDAO {
    private static ProductDAO instance = null;
    private Connection conn;
    private ObservableList<Product> listProducts = FXCollections.observableArrayList();
    private PreparedStatement allProductQuery, getProductQueryFromID, addProductQuery, getProductIDQuery, getProductQueryFromName,
            removeProductQuery, allProductsFromWarehouseQuerry;
    private int freeID;
    private Product currentProduct;

    private ProductDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allProductQuery = conn.prepareStatement("SELECT * FROM Product");
            getProductQueryFromID = conn.prepareStatement("SELECT * FROM Product WHERE id=?");
            getProductQueryFromName = conn.prepareStatement("SELECT * FROM Product WHERE name=?");
            addProductQuery = conn.prepareStatement("INSERT INTO Product VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            getProductIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Product");
            removeProductQuery = conn.prepareStatement("DELETE FROM Product WHERE id=?");
            allProductsFromWarehouseQuerry = conn.prepareStatement("SELECT * FROM Product WHERE Product.warehouse=?");
            ResultSet rs = getProductIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
            if(freeID<1)
                freeID = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistProducts();
    }

    public static ProductDAO getInstance() {
        if (instance == null)
            instance = new ProductDAO();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public Product getProductFromRS(ResultSet rs){
        Product p = null;
        try {
            p = new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4),
                    WarehouseDAO.getInstance().getWarehouse(rs.getInt(5)), rs.getInt(6), rs.getDouble(7),
                    CategoryDAO.getInstance().getCategory(rs.getInt(8)), ManufacturerDAO.getInstance().getManufacturer(rs.getInt(9)),
                    rs.getDate(10), rs.getDate(11), LocationDAO.getInstance().getLocation(rs.getInt(12)));
        } catch (SQLException e) {
            //
        }
        return p;
    }

    public Product getProduct(int id){
        Product p = null;
        try {
            getProductQueryFromID.setInt(1, id);
            ResultSet rs = getProductQueryFromID.executeQuery();
            if(rs.next())
                p = getProductFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public ObservableList<Product> getListProduct() {
        return listProducts;
    }

    public Product addProduct(Product p) {
        Product Product = getProduct(p.getName());
        if(Product!=null)
            return Product;

        try {
            addProductQuery.setInt(1, freeID);
            addProductQuery.setString(2, p.getName());
            addProductQuery.setDouble(3, p.getPrice());
            addProductQuery.setInt(4, p.getAmount());
            addProductQuery.setInt(5, p.getWarehouse().getId());
            addProductQuery.setInt(6, p.getGuarantee());
            addProductQuery.setDouble(7, p.getDiscount());
            addProductQuery.setInt(8, p.getCategory().getId());
            addProductQuery.setInt(9, p.getManufacturer().getId());
            addProductQuery.setDate(10, p.getLifetime());
            addProductQuery.setDate(11, p.getDateOfProduction());
            addProductQuery.setInt(12, p.getLocationOfProduction().getId());
            addProductQuery.executeUpdate();
            p.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistProducts();
        return p;
    }

    public Product getProduct(String s){
        Product c = null;
        try {
            getProductQueryFromName.setString(1, s);
            ResultSet rs = getProductQueryFromName.executeQuery();
            if(rs.next()){
                c = getProductFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    public void removeProduct(Product c){
        try {
            removeProductQuery.setInt(1, c.getId());
            removeProductQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistProducts();
    }

    public Map<Integer, Product> getProductsFromWarehouse(int warehouseID) {
        Map<Integer, Product> products = new HashMap<>();

        try {
            allProductsFromWarehouseQuerry.setInt(1, warehouseID);
            ResultSet rs = allProductsFromWarehouseQuerry.executeQuery();
            while(rs.next())
                products.put(rs.getInt(1), getProductFromRS(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    private void refreshlistProducts(){
        listProducts.clear();
        listProducts.addAll(getAllProducts());
    }

    private ArrayList<Product> getAllProducts() {
        ArrayList<Product> result = new ArrayList<>();
        try {
            ResultSet rs = allProductQuery.executeQuery();
            while (rs.next()) {
                result.add(getProductFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
