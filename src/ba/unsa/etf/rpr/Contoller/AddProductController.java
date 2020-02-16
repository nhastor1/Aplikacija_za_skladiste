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
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.Calendar;

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
    private Product product;

    public AddProductController(){
        product = null;
    }

    public AddProductController(Product p){
        product = p;
    }

    @FXML
    public void initialize(){
        choiceWarehouse.setItems(WarehouseDAO.getInstance().getListWarehouse());
        choiceCategory.setItems(CategoryDAO.getInstance().getListCategory());
        choiceManufacturer.setItems(ManufacturerDAO.getInstance().getListManufacturer());
        choiceLoaction.setItems(LocationDAO.getInstance().getListLocation());
        setProduct();
    }

    public void okAction(ActionEvent actionEvent) {
        if(isValid()){
            Product p = ProductDAO.getInstance().addProduct(new Product(
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
            System.out.println(p.getDateOfProduction().getYear() + "-" + p.getDateOfProduction().getMonth() + "-" + p.getDateOfProduction().getDay());
            System.out.println(p.getLifetime().getYear() + "-" + p.getLifetime().getMonth() + "-" + p.getLifetime().getDay());
            cancelAction(actionEvent);
        }
        else{
            ErrorBox("Some fields are empty or incorrect", "You need to enter those fields");
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
        Date date = new Date(datePicker.getValue().getYear(), datePicker.getValue().getMonthValue(), datePicker.getValue().getDayOfMonth());
        System.out.println(datePicker.getValue().getYear() + "-" + datePicker.getValue().getMonthValue() + "-" + datePicker.getValue().getDayOfMonth());
        return date;
    }

    private void setProduct(){
        if(product==null)
            return;

        fldPrice.setText(product.getPrice() + "");
        fldName.setText(product.getName());
        fldAmount.setText(product.getAmount() + "");
        choiceWarehouse.getSelectionModel().select(product.getWarehouse());
        fldGuarantee.setText(product.getAmount() + "");
        choiceCategory.getSelectionModel().select(product.getCategory());
        choiceManufacturer.getSelectionModel().select(product.getManufacturer());
        choiceLoaction.getSelectionModel().select(product.getLocationOfProduction());
        dateProduction.setDisable(true);
        dateLifeTime.setDisable(true);
    }

    private void ErrorBox(String s1, String s2) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(s1);
        alert.setContentText(s2);

        alert.showAndWait();
    }
}
