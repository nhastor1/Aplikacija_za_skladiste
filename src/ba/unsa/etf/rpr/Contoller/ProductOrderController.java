package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Bundle;
import ba.unsa.etf.rpr.DAO.InvoiceDAO;
import ba.unsa.etf.rpr.DAO.ProductOrderDAO;
import ba.unsa.etf.rpr.Invoice;
import ba.unsa.etf.rpr.Person.LegalPerson;
import ba.unsa.etf.rpr.Person.NaturalPerson;
import ba.unsa.etf.rpr.ProductOrder;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

public class ProductOrderController {
    public TableView<ProductOrder> tableViewProductOrder;
    public TableColumn<ProductOrder, Integer> colID;
    public TableColumn<ProductOrder, String> colProduct;
    public TableColumn<ProductOrder, Integer> colAmount;
    public TableColumn<ProductOrder, Date> colDate;
    private Scene scene;
    private ProductOrderDAO dao;
    private ArrayList<ProductOrder> producOrders = null;
    private NaturalPerson naturalPerson = null;
    private double discount = 0;

    public ProductOrderController(Scene scene) {
        this.scene = scene;
    }

    @FXML
    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("Product"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("TimeOfOrder"));

        dao = ProductOrderDAO.getInstance();
        tableViewProductOrder.setItems(dao.getListProductOrder());

        tableViewProductOrder.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            dao.setCurrentProductOrder(newSelection);
        });
    }

    public void backAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.setScene(scene);
    }

    public void removeAction(ActionEvent actionEvent) {
        if(dao.getCurrentProductOrder()==null)
            return;
        dao.removeProductOrder(dao.getCurrentProductOrder());
        dao.setCurrentProductOrder(null);
    }

    public void addAction(ActionEvent actionEvent) {
        producOrders = new ArrayList<>();

        try {
            AddInvoiceController ctrl = new AddInvoiceController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addInvoice.fxml"), Bundle.get());

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(Bundle.get().getString("newInvoice"));
            stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
            stage.setOnHiding((event) -> {
                naturalPerson = ctrl.getNaturalPerson();
                discount = ctrl.getDiscount();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tableViewProductOrder.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(Bundle.get().getString("ConfirmationDialog"));
                alert.setHeaderText(Bundle.get().getString("Doyouwanttoaddthisproductordertoinvoice"));

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK)
                    producOrders.add(tableViewProductOrder.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void finishInvoiceAction(ActionEvent actionEvent){
        if(getProducOrders()!=null){
            double price = 0;
            for(ProductOrder p : getProducOrders())
                price += p.getPrice();
            Invoice invoice = InvoiceDAO.getInstance().addInvoice(new Invoice(0, naturalPerson, price, discount));
            for(ProductOrder p : getProducOrders()) {
                p.setInvoice(invoice);
                ProductOrderDAO.getInstance().addProductOrder(p);
            }
        }
        backAction(actionEvent);
    }

    public ArrayList<ProductOrder> getProducOrders() {
        return producOrders;
    }
}
