package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Category;
import ba.unsa.etf.rpr.DAO.*;
import ba.unsa.etf.rpr.Location.Location;
import ba.unsa.etf.rpr.Manufacturer;
import ba.unsa.etf.rpr.Product;
import ba.unsa.etf.rpr.Warehouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AddProductController {
    public TextField fldPrice;
    public TextField fldName;
    public TextField fldAmount;
    public ChoiceBox<Warehouse> choiceWarehouse;
    public TextField fldGuarantee;
    public ChoiceBox<Category> choiceCategory;
    public ChoiceBox<Manufacturer> choiceManufacturer;
    public ChoiceBox<Location> choiceLoaction;
    public DatePicker dateProduction;
    public DatePicker dateLifeTime;

    @FXML
    public void initialize(){
        choiceWarehouse.setItems(WarehouseDAO.getInstance().getListWarehouse());
        choiceCategory.setItems(CategoryDAO.getInstance().getListCategory());
        choiceManufacturer.setItems(ManufacturerDAO.getInstance().getListManufacturer());
        choiceLoaction.setItems(LocationDAO.getInstance().getListLocation());
    }

    public void okAction(ActionEvent actionEvent) {
        if(isValid()){
            ProductDAO.getInstance().addProduct(new Product(
                    0,
                    fldName.getText(),
                    Double.parseDouble(fldPrice.getText()),
                    Integer.parseInt(fldAmount.getText()),
                    choiceWarehouse.getSelectionModel().getSelectedItem(),
                    getGuarantee(),
                    0,
                    choiceCategory.getSelectionModel().getSelectedItem(),
                    choiceManufacturer.getSelectionModel().getSelectedItem(),
                    getDate(dateLifeTime),
                    getDate(dateProduction),
                    choiceLoaction.getSelectionModel().getSelectedItem()
                    ));
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    private boolean isValid(){
        if(fldPrice.getText().equals(""))
            return false;

        if(choiceCategory.getSelectionModel().getSelectedItem()==null ||
            choiceLoaction.getSelectionModel().getSelectedItem()==null ||
            choiceManufacturer.getSelectionModel().getSelectedItem()==null ||
            choiceWarehouse.getSelectionModel().getSelectedItem()==null)
            return false;

        if(!isNumber(fldPrice.getText()) || !isInteger(fldAmount.getText()) ||
                (!isInteger(fldGuarantee.getText()) && !fldGuarantee.getText().equals("")))
            return false;

        return true;
    }

    private int getGuarantee(){
        if(fldGuarantee.getText().equals(""))
            return 0;
        return Integer.parseInt(fldGuarantee.getText());
    }

    private Date getDate(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }
}
