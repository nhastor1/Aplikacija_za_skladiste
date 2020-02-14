package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.DAO.ContinentDAO;
import ba.unsa.etf.rpr.DAO.LocationDAO;
import ba.unsa.etf.rpr.Location.Continent;
import ba.unsa.etf.rpr.Location.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LocationController {
    public ChoiceBox<Location> choiceLocation;
    public ChoiceBox<Continent> choiceContinent;
    public TextField fldCountry;
    public TextField fldCity;
    public TextField fldStreet;
    public TextField fldNumber;
    private Location location;

    @FXML
    public void initialize(){
        choiceLocation.setItems(LocationDAO.getInstance().getListLocation());
        choiceContinent.setItems(ContinentDAO.getInstance().getListContinent());

        choiceLocation.showingProperty().addListener((newLocation) ->{
            if(newLocation!=null) {
                choiceContinent.setDisable(true);
                fldCountry.setDisable(true);
                fldCity.setDisable(true);
                fldStreet.setDisable(true);
                fldNumber.setDisable(true);
            }
            else{
                choiceContinent.setDisable(false);
                fldCountry.setDisable(false);
                fldCity.setDisable(false);
                fldStreet.setDisable(false);
                fldNumber.setDisable(false);
            }
        });

    }

    public void okAction(ActionEvent actionEvent) {

    }

    public void cancelAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
