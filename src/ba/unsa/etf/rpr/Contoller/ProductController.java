package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Bundle;
import ba.unsa.etf.rpr.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAO.ProductOrderDAO;
import ba.unsa.etf.rpr.Product;
import ba.unsa.etf.rpr.ProductOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;

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
        setImage(product.getImage());
    }

    public void okAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void changeProduct(ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProduct.fxml"), Bundle.get());
            AddProductController ctrl = new AddProductController(product);
            loader.setController(ctrl);

            Stage stage = new Stage();
            root = loader.load();
            stage.setTitle(Bundle.get().getString("changeProduct"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setOnHiding((event) -> {
                product = ctrl.getProduct();
                ProductDAO.getInstance().modifyProduct(product);
                refreshScene();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeImageAction(ActionEvent actionEvent) {
        Parent root = null;
        try {
            Stage myStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/searchImage.fxml"), Bundle.get());
            SearchImageController ctrl = new SearchImageController();
            loader.setController(ctrl);
            root = loader.load();

            myStage.setTitle(Bundle.get().getString("search"));
            myStage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.setOnHiding((event) -> {
                String s = ctrl.getImageToSet();
                if(!s.isEmpty()){
                    setImage(s);
                    product.setImage(s);
                    ProductDAO.getInstance().modifyProduct(product);
                }
            });
            myStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void productOrederAction(ActionEvent actionEvent){
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle(Bundle.get().getString("TextInputDialog"));
        dialog.setHeaderText(Bundle.get().getString("Enteramountofproduct"));


        Optional<String> result = dialog.showAndWait();

        result.ifPresent(amount -> {
            if(isInteger(amount)){
                makeNewProductOrder(Integer.parseInt(amount));
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Bundle.get().getString("ErrorDialog"));
                alert.setHeaderText(Bundle.get().getString("Numberyouinputisnotvalid"));
                alert.setContentText(Bundle.get().getString("Youneedtoinputaninteger"));

                alert.showAndWait();
            }

        });
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

    private void setImage(String url){
        if(url.isEmpty())
            return;

        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(128);
        imageView.setFitWidth(128);
        btnImg.setGraphic(imageView);
    }

    private void refreshScene(){
        Stage window = (Stage) btnImg.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"), Bundle.get());
        loader.setController(this);
        try {
            window.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }


    private void makeNewProductOrder(int amount) {
        ProductOrderDAO.getInstance().addProductOrder(new ProductOrder(
                0, product, amount, null, 0
        ));
        System.out.println("New product order added");
    }
}
