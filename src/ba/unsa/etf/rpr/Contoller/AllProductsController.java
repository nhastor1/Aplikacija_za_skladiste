package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Bundle;
import ba.unsa.etf.rpr.DAO.ProductDAO;
import ba.unsa.etf.rpr.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class AllProductsController {
    public TableView<Product> tableViewProduct;
    public TableColumn<Product, Integer> colID;
    public TableColumn<Product, String> colCategory;
    public TableColumn<Product, String> colName;
    public TableColumn<Product, Integer> colAmount;
    public TableColumn<Product, Double> colPrice;

    private ProductDAO dao = ProductDAO.getInstance();
    private Scene scene;

    public AllProductsController(Scene scene){
        this.scene = scene;
    }

    @FXML
    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        tableViewProduct.setItems(dao.getListProduct());

        tableViewProduct.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            dao.setCurrentProduct(newSelection);
        });

        tableViewProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        productAction(mouseEvent);
                    }
                }
            }
        });
    }

    public void backAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.setScene(scene);
    }

    public void removeAction(ActionEvent actionEvent) {
        if(dao.getCurrentProduct()==null)
            return;
        dao.removeProduct(dao.getCurrentProduct());
        System.out.println("Deleted product");
        dao.setCurrentProduct(null);
    }

    public void addAction(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProduct.fxml"), Bundle.get());
            AddProductController ctrl = new AddProductController();
            loader.setController(ctrl);

            Stage stage = new Stage();
            root = loader.load();
            stage.setTitle(Bundle.get().getString("addProduct"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void productAction(MouseEvent mouseEvent){
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"), Bundle.get());
            ProductController ctrl = new ProductController(tableViewProduct.getSelectionModel().getSelectedItem().getId());
            loader.setController(ctrl);

            Stage stage = new Stage();
            root = loader.load();
            stage.setTitle(Bundle.get().getString("product"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
