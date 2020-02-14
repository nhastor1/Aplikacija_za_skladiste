package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.DAO.ManufacturerDAO;
import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Manufacturer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


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
        colLocation.setCellValueFactory(new PropertyValueFactory<>("Addres"));

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
        if(dao.getCurrentManufacturer()==null)
            return;
        dao.removeManufacturer(dao.getCurrentManufacturer());
        dao.setCurrentManufacturer(null);
    }

    public void addAction(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addManufacturer.fxml"));
            AddManufacturerController ctrl = new AddManufacturerController();
            loader.setController(ctrl);

            Stage stage = new Stage();
            root = loader.load();
            stage.setTitle("Manufacturer");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
