package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Bundle;
import ba.unsa.etf.rpr.Category;
import ba.unsa.etf.rpr.DAO.CategoryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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
        colSuperCategory.setCellValueFactory(new PropertyValueFactory<>("Supercategory"));

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
        if(!dao.removeCategory(dao.getCurrentCategory())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Bundle.get().getString("ErrorDialog"));
            alert.setHeaderText(Bundle.get().getString("Cannotdeletecategory"));
            alert.setContentText(Bundle.get().getString("Someproducthasthiscategoryorthiscategoryissupercategoryofsomecategories"));

            alert.showAndWait();
        }
        dao.setCurrentCategory(null);
    }

    public void addAction(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addCategory.fxml"), Bundle.get());
            AddCategoryController ctrl = new AddCategoryController();
            loader.setController(ctrl);

            Stage stage = new Stage();
            root = loader.load();
            stage.setTitle(Bundle.get().getString("addCategory"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
