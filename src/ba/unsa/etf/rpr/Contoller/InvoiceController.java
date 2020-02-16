package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.DAO.InvoiceDAO;
import ba.unsa.etf.rpr.Person.LegalPerson;
import ba.unsa.etf.rpr.Invoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;

public class InvoiceController {
    public TableView<Invoice> tableViewInvoice;
    public TableColumn<Invoice, Integer> colID;
    public TableColumn<Invoice, String> colCustomer;
    public TableColumn<Invoice, Double> colPrice;
    public TableColumn<Invoice, Date> colDate;
    private Scene scene;
    private InvoiceDAO dao;

    public InvoiceController(Scene scene) {
        this.scene = scene;
    }

    @FXML
    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("TimeOfOrder"));

        dao = InvoiceDAO.getInstance();
        tableViewInvoice.setItems(dao.getListInvoice());

        tableViewInvoice.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            dao.setCurrentInvoice(newSelection);
        });
    }

    public void backAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.setScene(scene);
    }

    public void removeAction(ActionEvent actionEvent) {
        if(dao.getCurrentInvoice()==null)
            return;
        dao.removeInvoice(dao.getCurrentInvoice());
        dao.setCurrentInvoice(null);
    }
}
