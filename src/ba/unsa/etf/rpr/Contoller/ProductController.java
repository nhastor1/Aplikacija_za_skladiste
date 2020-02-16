package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.DAO.ProductDAO;
import ba.unsa.etf.rpr.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ProductController {
    public Label lblID;
    public Label lblName;
    public Label lblPrice;
    public Label lblAmount;
    public Label lblWarehouse;
    public Label lblGuarantee;
    public Label lblDiscount;
    public Label lblCategory;
    public Label lblManufacturer;
    public Label lblLocation;
    public Label lblDateProduction;
    public Label lblTime;
    public Button btnImg;
    private int id;
    private Product product;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");

    public ProductController(int id){
        this.id = id;
    }

    @FXML
    public void initialize(){
        refreshData();
    }

    public void okAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void changeProduct(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProduct.fxml"));
            AddProductController ctrl = new AddProductController(product);
            loader.setController(ctrl);

            Stage stage = new Stage();
            root = loader.load();
            stage.setTitle("Change product");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeImageAction(ActionEvent actionEvent) {
    }

    private void refreshData(){
        product = ProductDAO.getInstance().getProduct(id);
        lblID.setText(product.getId() + "");
        lblName.setText(product.getName());
        lblPrice.setText(product.getPrice() + "");
        lblAmount.setText(product.getAmount() + "");
        lblWarehouse.setText(product.getWarehouse().getName());
        lblGuarantee.setText(product.getGuarantee() + "");
        lblDiscount.setText(product.getDiscount() + "");
        lblCategory.setText(product.getCategory().getName());
        lblManufacturer.setText(product.getManufacturer().getName());
        lblLocation.setText(product.getLocationOfProduction() + "");
        lblDateProduction.setText(formatDate(product.getDateOfProduction()));
        lblTime.setText(formatDate(product.getLifetime()));
    }

    private String formatDate(Date date) {
        return sdf.format(new Date(date.getTime()));
    }
}
