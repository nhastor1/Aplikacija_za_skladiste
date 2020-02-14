package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Category;
import ba.unsa.etf.rpr.DAO.CategoryDAO;
import ba.unsa.etf.rpr.DAO.ManufacturerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CategoryController {
    public TableView<Category> tableViewCategory;
    public TableColumn<Category, Integer> colID;
    public TableColumn<Category, String> colCategory;
    public TableColumn<Category, String> colSuperCategory;
    private Scene scene;
    private CategoryDAO dao = null;

    public CategoryController(Scene scene){
        this.scene = scene;
    }

    @FXML
    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colSuperCategory.setCellValueFactory(new PropertyValueFactory<>("Supecategory"));

        dao = CategoryDAO.getInstance();
        tableViewCategory.setItems(dao.getListCategory());

        tableViewCategory.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            dao.setCurrentCategory(newSelection);
        });
    }

    public void backAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.setScene(scene);
    }

    public void removeAction(ActionEvent actionEvent) {
        if(dao.getCurrentCategory()==null)
            return;
        dao.removeCategory(dao.getCurrentCategory());
        dao.setCurrentCategory(null);
    }

    public void addAction(ActionEvent actionEvent) {
    }
}
