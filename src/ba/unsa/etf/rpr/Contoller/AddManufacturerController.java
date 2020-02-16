package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.DAO.LegalPersonDAO;
import ba.unsa.etf.rpr.DAO.ManufacturerDAO;
import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Manufacturer;
import ba.unsa.etf.rpr.Person.LegalPerson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class AddManufacturerController {
    public TextField fldName;
    private Location location = null;

    public void addLocationAction(ActionEvent actionEvent) {
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/enterLocation.fxml"), bundle);
            LocationController ctrl = new LocationController();
            loader.setController(ctrl);

            Stage stage = new Stage();
            root = loader.load();
            stage.setTitle("Location");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setOnHiding((event) ->{
                if(ctrl.isSet()){
                    location = ctrl.getLocation();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void okAction(ActionEvent actionEvent) {
        if(fldName.getText().equals("") || location==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Incorrect name or location");
            alert.setContentText("You need to enter name and location");

            alert.showAndWait();
        }
        else{
            LegalPerson lp = LegalPersonDAO.getInstance().addLegalPerson(new LegalPerson(0, location, fldName.getText()));
            Manufacturer m = ManufacturerDAO.getInstance().addManufacturer(new Manufacturer(0, lp));
            cancelAction(actionEvent);
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}