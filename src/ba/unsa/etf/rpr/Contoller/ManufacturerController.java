package ba.unsa.etf.rpr.Contoller;

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
    Scene scene;
    Button btnBack;
    TableView<Manufacturer>  tableViewManufacturer;
    TableColumn<Manufacturer, Integer> colID;
    TableColumn<Manufacturer, String> colName;
    TableColumn<Manufacturer, String> colLocation;

    public ManufacturerController(Scene scene) {
        this.scene = scene;
    }

    @FXML
    public void initialize(){
//        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
//        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        colLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));

        //dao = GeografijaDAO.getInstance();
        //tableViewManufacturer.setItems(dao.getListaGradova());

//        tableViewManufacturer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            dao.setTrenutni(newSelection);
//        });
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
