package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.DAO.ManufacturerDAO;
import ba.unsa.etf.rpr.Manufacturer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ManufacturerController {
    private Scene scene;
    public Button btnBack;
    public TableView<Manufacturer>  tableViewManufacturer;
    public TableColumn<Manufacturer, Integer> colID;
    public TableColumn<Manufacturer, String> colName;
    public TableColumn<Manufacturer, String> colLocation;
    private ManufacturerDAO dao = null;

    public ManufacturerController(Scene scene) {
        this.scene = scene;
    }

    @FXML
    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));

        dao = ManufacturerDAO.getInstance();
        tableViewManufacturer.setItems(dao.getListManufacturer());

        tableViewManufacturer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            dao.setCurrentManufacturer(newSelection);
        });
    }

    public void backAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.setScene(scene);
    }

    public void removeAction(ActionEvent actionEvent) {
    }

    public void addAction(ActionEvent actionEvent) {
    }
}
