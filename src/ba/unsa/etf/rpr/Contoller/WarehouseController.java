package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.DAO.WarehouseDAO;
import ba.unsa.etf.rpr.Warehouse;
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

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class WarehouseController {

    public TableView<Warehouse> tableViewWarehouse;
    public TableColumn<Warehouse, Integer> colID;
    public TableColumn<Warehouse, String> colName;
    public TableColumn<Warehouse, Integer> colLocation;
    private WarehouseDAO dao = WarehouseDAO.getInstance();
    private Scene scene;

    public WarehouseController(Scene scene){
        this.scene = scene;
    }

    @FXML
    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));

        tableViewWarehouse.setItems(dao.getListWarehouse());

        tableViewWarehouse.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            dao.setCurrentWarehouse(newSelection);
        });
    }


    public void backAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.setScene(scene);
    }

    public void removeAction(ActionEvent actionEvent) {
        if(dao.getCurrentWarehouse()==null)
            return;
        if(!dao.removeWarehouse(dao.getCurrentWarehouse())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Cannot delete Warehouse");
            alert.setContentText("Some product has this Warehouse,\nor this Warehouse is superWarehouse\nof some categories");

            alert.showAndWait();
        }
        dao.setCurrentWarehouse(null);
    }

    public void addAction(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addWarehouse.fxml"));
            AddWarehouseController ctrl = new AddWarehouseController();
            loader.setController(ctrl);

            Stage stage = new Stage();
            root = loader.load();
            stage.setTitle("Add Warehouse");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
