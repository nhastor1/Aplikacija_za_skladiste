package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Bundle;
import ba.unsa.etf.rpr.DAO.CityDAO;
import ba.unsa.etf.rpr.DAO.ContinentDAO;
import ba.unsa.etf.rpr.DAO.CountryDAO;
import ba.unsa.etf.rpr.DAO.LocationDAO;
import ba.unsa.etf.rpr.Location.City;
import ba.unsa.etf.rpr.Location.Continent;
import ba.unsa.etf.rpr.Location.Country;
import ba.unsa.etf.rpr.Location.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
    private boolean set = false;

    @FXML
    public void initialize(){
        choiceLocation.setItems(LocationDAO.getInstance().getListLocation());
        choiceContinent.setItems(ContinentDAO.getInstance().getListContinent());

        choiceLocation.showingProperty().addListener((newLocation) ->{
            if(choiceLocation.getSelectionModel().getSelectedItem()!=null) {
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
        if(choiceLocation.getSelectionModel().getSelectedItem()==null){
            if(choiceContinent.getSelectionModel().getSelectedItem()==null || fldCountry.getText().equals("")
                || fldCity.getText().equals("") || fldStreet.getText().equals("") || fldNumber.getText().equals("")
                || !isInteger(fldNumber.getText())){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Bundle.get().getString("ErrorDialog"));
                alert.setHeaderText(Bundle.get().getString("Incorrectlocation"));
                alert.setContentText(Bundle.get().getString("Youneedtoenterallfieldsorchoosepersistentlocation"));

                alert.showAndWait();
            }
            else{
                Continent continent = ContinentDAO.getInstance().getContinent(choiceContinent.getSelectionModel().getSelectedIndex()+1);
                Country country = CountryDAO.getInstance().addCountry(new Country(0, fldCountry.getText(), continent));
                City city = CityDAO.getInstance().addCity(new City(0, fldCity.getText(), country));
                location = LocationDAO.getInstance().addLocation(new Location(0, fldStreet.getText(),
                        Integer.parseInt(fldNumber.getText()), city));

                set = true;
                cancelAction(actionEvent);
            }
        }
        else{
            location = choiceLocation.getSelectionModel().getSelectedItem();
            set = true;
            cancelAction(actionEvent);
        }
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

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }

    public boolean isSet() {
        return set;
    }
}
