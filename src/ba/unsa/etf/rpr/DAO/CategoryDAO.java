package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.Location.Continent;
import ba.unsa.etf.rpr.Category;
import ba.unsa.etf.rpr.Manufacturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {
    private static CategoryDAO instance = null;
    private Connection conn;
    private ObservableList<Category> listCategories = FXCollections.observableArrayList();
    private PreparedStatement allCategoryQuery, getCategoryQueryFromID, addCategoryQuery, getCategoryIDQuery, getCategoryQueryFromName, removeManufacturerQuery;
    private int freeID;
    private Category currentCategory;

    private CategoryDAO() {
        conn = MainDAO.getInstance().getConn();
        try {
            allCategoryQuery = conn.prepareStatement("SELECT * FROM Category");
            getCategoryQueryFromID = conn.prepareStatement("SELECT * FROM Category WHERE id=?");
            getCategoryQueryFromName = conn.prepareStatement("SELECT * FROM Category WHERE name=?");
            addCategoryQuery = conn.prepareStatement("INSERT INTO Category VALUES(?,?,?)");
            getCategoryIDQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM Category");
            removeManufacturerQuery = conn.prepareStatement("DELETE FROM Category WHERE id=?");
            ResultSet rs = getCategoryIDQuery.executeQuery();
            rs.next();
            freeID = rs.getInt(1);
            if(freeID<1)
                freeID = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistCategories();
    }

    public static CategoryDAO getInstance() {
        if (instance == null)
            instance = new CategoryDAO();
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

    public Category getCategoryFromRS(ResultSet rs){
        Category c = null;
        try {
            Category cont = CategoryDAO.getInstance().getCategory(rs.getInt(3));
            c = new Category(rs.getInt(1), rs.getString(2), cont);
        } catch (SQLException e) {
            //
        }
        return c;
    }

    public Category getCategory(int id){
        Category c = null;
        try {
            getCategoryQueryFromID.setInt(1, id);
            ResultSet rs = getCategoryQueryFromID.executeQuery();
            c = getCategoryFromRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public ObservableList<Category> getListCategory() {
        return listCategories;
    }

    public Category addCategory(Category c) {
        Category Category = getCategory(c.getName());
        if(Category!=null)
            return Category;

        try {
            addCategoryQuery.setInt(1, freeID);
            addCategoryQuery.setString(2, c.getName());

            int supercategoryID = 0;
            if(c.getSupercategory()!=null)
                supercategoryID = c.getSupercategory().getId();
            addCategoryQuery.setInt(3, supercategoryID);

            addCategoryQuery.executeUpdate();
            c.setId(freeID);
            freeID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshlistCategories();
        return c;
    }

    public Category getCategory(String s){
        Category c = null;
        try {
            getCategoryQueryFromName.setString(1, s);
            ResultSet rs = getCategoryQueryFromName.executeQuery();
            if(rs.next()){
                c = getCategoryFromRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public void removeCategory(Category c){
        try {
            removeManufacturerQuery.setInt(1, c.getId());
            removeManufacturerQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshlistCategories();
    }

    private void refreshlistCategories(){
        listCategories.clear();
        listCategories.addAll(getAllCategories());
    }

    private ArrayList<Category> getAllCategories() {
        ArrayList<Category> result = new ArrayList<>();
        try {
            ResultSet rs = allCategoryQuery.executeQuery();
            while (rs.next()) {
                result.add(getCategoryFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
