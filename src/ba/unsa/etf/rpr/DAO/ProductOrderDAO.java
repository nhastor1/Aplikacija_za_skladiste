package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.ProductOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductOrderDAO {
    private static ProductOrderDAO instance = null;
    private Connection conn;
    private ObservableList<ProductOrder> listProductOrders = FXCollections.observableArrayList();
    private PreparedStatement allProductOrderQuery, getProductOrderQueryFromID, addProductOrderQuery, getProductOrderIDQuery,
         removeProductOrderQuery;
    private int freeID;
    private ProductOrder currentProductOrder;

    private ProductOrderDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allProductOrderQuery = conn.prepareStatement("SELECT * FROM Product_order");
            getProductOrderQueryFromID = conn.prepareStatement("SELECT * FROM Product_order WHERE id=?");
            addProductOrderQuery = conn.prepareStatement("INSERT INTO Product_order VALUES(?,?,?,?,?,?)");
            getProductOrderIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Product_order");
            removeProductOrderQuery = conn.prepareStatement("DELETE FROM Product_order WHERE id=?");
            ResultSet rs = getProductOrderIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
            if(freeID<1)
                freeID = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistProductOrders();
    }

    public static ProductOrderDAO getInstance() {
        if (instance == null)
            instance = new ProductOrderDAO();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public ProductOrder getProductOrderFromRS(ResultSet rs){
        ProductOrder p = null;
        try {
            p = new ProductOrder(rs.getInt(1), ProductDAO.getInstance().getProduct(rs.getInt(2)),
                    rs.getInt(3), InvoiceDAO.getInstance().getInvoice(rs.getInt(4)),
                    rs.getDouble(5), rs.getDate(6));
        } catch (SQLException e) {
            //
        }
        return p;
    }

    public ProductOrder getProductOrder(int id){
        ProductOrder p = null;
        try {
            getProductOrderQueryFromID.setInt(1, id);
            ResultSet rs = getProductOrderQueryFromID.executeQuery();
            if(rs.next())
                p = getProductOrderFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public ObservableList<ProductOrder> getListProductOrder() {
        return listProductOrders;
    }

    public ProductOrder addProductOrder(ProductOrder p) {
        try {
            addProductOrderQuery.setInt(1, freeID);
            addProductOrderQuery.setInt(2, p.getProduct().getId());
            addProductOrderQuery.setInt(3, p.getAmount());

            int invoice = 0;
            if(p.getInvoice()!=null)
                invoice = p.getInvoice().getId();
            addProductOrderQuery.setInt(4, invoice);

            addProductOrderQuery.setDouble(5, p.getDiscount());
            addProductOrderQuery.setDate(6, p.getTimeOfOrder());
            addProductOrderQuery.executeUpdate();
            p.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistProductOrders();
        return p;
    }

    public ProductOrder getCurrentProductOrder() {
        return currentProductOrder;
    }

    public void setCurrentProductOrder(ProductOrder currentProductOrder) {
        this.currentProductOrder = currentProductOrder;
    }

    public boolean removeProductOrder(ProductOrder c){
        if(!canRemoveProductOrder(c))
            return false;
        try {
            removeProductOrderQuery.setInt(1, c.getId());
            removeProductOrderQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistProductOrders();
        return true;
    }

    private void refreshlistProductOrders(){
        listProductOrders.clear();
        listProductOrders.addAll(getAllProductOrders());
    }

    private ArrayList<ProductOrder> getAllProductOrders() {
        ArrayList<ProductOrder> result = new ArrayList<>();
        try {
            ResultSet rs = allProductOrderQuery.executeQuery();
            while (rs.next()) {
                result.add(getProductOrderFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    private boolean canRemoveProductOrder(ProductOrder c) {
        return c.getInvoice()==null;
    }
}
