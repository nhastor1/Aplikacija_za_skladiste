package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.DAO.InvoiceDAO;
import ba.unsa.etf.rpr.DAO.ProductOrderDAO;
import ba.unsa.etf.rpr.Exception.InvalidPasswordException;
import ba.unsa.etf.rpr.Exception.InvalidUsernameException;
import ba.unsa.etf.rpr.Invoice;
import ba.unsa.etf.rpr.ProductOrder;
import ba.unsa.etf.rpr.User.User;
import ba.unsa.etf.rpr.User.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class FrontPageController {
    private UsersModel usersModel;
    private Scene scene;
    private Stage primaryStage;
    public Button btnUser;

    public FrontPageController(UsersModel usersModel, Scene scene){
        this.usersModel = usersModel;
        this.scene = scene;
    }

    public void closeAction(ActionEvent actionEvent){
        Stage stage = (Stage) btnUser.getScene().getWindow();
        stage.setScene(scene);
    }

    public void userAction(ActionEvent actionEvent){
        try {
            usersModel.napuni();
            UserController ctrl = new UserController(usersModel);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users.fxml"));
            loader.setController(ctrl);
            Parent root = null;

            root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Front Page");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            UsersModel finalModel = usersModel;
            stage.setOnHiding((event)->{
                try {
                    finalModel.updateAll();
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void manufacturerAction(ActionEvent actionEvent){
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            ManufacturerController ctrl = new ManufacturerController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manufacturer.fxml"));

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle("Manufacturers");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void warehouseAction(ActionEvent actionEvent) {
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            WarehouseController ctrl = new WarehouseController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/warehouse.fxml"));

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle("Warehouse");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void categoryAction(ActionEvent actionEvent) {
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            CategoryController ctrl = new CategoryController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle("Category");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void productAction(ActionEvent actionEvent) {
    }

    public void allProductAction(ActionEvent actionEvent) {
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            AllProductsController ctrl = new AllProductsController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/allProducts.fxml"));

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle("All products");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchAction(ActionEvent actionEvent) {
    }

    public void locationAction(ActionEvent actionEvent) {
    }

    public void inoviceAction(ActionEvent actionEvent) {
    }

    public void productOrederAction(ActionEvent actionEvent) {
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            ProductOrderController ctrl = new ProductOrderController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productOrder.fxml"));

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle("Product order");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void employeesAction(ActionEvent actionEvent) {
    }
}
