package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Invoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceDAO {
    private static InvoiceDAO instance = null;
    private Connection conn;
    private ObservableList<Invoice> listInvoices = FXCollections.observableArrayList();
    private PreparedStatement allInvoiceQuery, getInvoiceQueryFromID, addInvoiceQuery, getInvoiceIDQuery, removeInvoiceQuery;
    private int freeID;
    private Invoice currentInvoice;

    private InvoiceDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allInvoiceQuery = conn.prepareStatement("SELECT * FROM Invoice");
            getInvoiceQueryFromID = conn.prepareStatement("SELECT * FROM Invoice WHERE id=?");
            addInvoiceQuery = conn.prepareStatement("INSERT INTO Invoice VALUES(?,?,?,?,?)");
            getInvoiceIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Invoice");
            removeInvoiceQuery = conn.prepareStatement("DELETE FROM Invoice WHERE id=?");
            ResultSet rs = getInvoiceIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
            if(freeID<1)
                freeID = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistInvoices();
    }

    public static InvoiceDAO getInstance() {
        if (instance == null)
            instance = new InvoiceDAO();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public Invoice getInvoiceFromRS(ResultSet rs){
        Invoice p = null;
        try {
            p = new Invoice(rs.getInt(1), LegalPersonDAO.getInstance().getLegalPerson(rs.getInt(2)), rs.getDouble(3),
                    rs.getDouble(4), rs.getDate(5));
        } catch (SQLException e) {
            //
        }
        return p;
    }

    public Invoice getInvoice(int id){
        Invoice p = null;
        try {
            getInvoiceQueryFromID.setInt(1, id);
            ResultSet rs = getInvoiceQueryFromID.executeQuery();
            if(rs.next())
                p = getInvoiceFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public ObservableList<Invoice> getListInvoice() {
        return listInvoices;
    }

    public Invoice addInvoice(Invoice p) {
        try {
            addInvoiceQuery.setInt(1, freeID);
            addInvoiceQuery.setInt(2, p.getCustomer().getId());
            addInvoiceQuery.setDouble(3, p.getPrice());
            addInvoiceQuery.setDouble(4, p.getDiscount());
            addInvoiceQuery.setDate(5, p.getTimeOfOrder());
            addInvoiceQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistInvoices();
        return p;
    }

    public Invoice getCurrentInvoice() {
        return currentInvoice;
    }

    public void setCurrentInvoice(Invoice currentInvoice) {
        this.currentInvoice = currentInvoice;
    }

    public void removeInvoice(Invoice c){
        try {
            removeInvoiceQuery.setInt(1, c.getId());
            removeInvoiceQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistInvoices();
    }

//    public Invoice modifyInvoice(Invoice p) {
//        Invoice Invoice = getInvoice(p.getId());
//        if(Invoice==null)
//            return null;
//
//        try {
//            modifyInvoiceQuery.setDouble(1, p.getDiscount());
//            modifyInvoiceQuery.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        refreshlistInvoices();
//        return p;
//    }

    private void refreshlistInvoices(){
        listInvoices.clear();
        listInvoices.addAll(getAllInvoices());
    }

    private ArrayList<Invoice> getAllInvoices() {
        ArrayList<Invoice> result = new ArrayList<>();
        try {
            ResultSet rs = allInvoiceQuery.executeQuery();
            while (rs.next()) {
                result.add(getInvoiceFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
