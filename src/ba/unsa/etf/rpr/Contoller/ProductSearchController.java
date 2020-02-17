package ba.unsa.etf.rpr.Contoller;


import ba.unsa.etf.rpr.Bundle;
import ba.unsa.etf.rpr.DAO.ProductDAO;
import ba.unsa.etf.rpr.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ProductSearchController {
    public TextField fldName;

    public void searchAction(ActionEvent actionEvent){
        Product p = ProductDAO.getInstance().getProduct(fldName.getText());
        if(p==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Bundle.get().getString("ErrorDialog"));
            alert.setHeaderText(Bundle.get().getString("NoProduct"));

            alert.showAndWait();
        }
        else{
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"), Bundle.get());
                ProductController ctrl = new ProductController(p.getId());
                loader.setController(ctrl);

                Stage stage = new Stage();
                root = loader.load();
                stage.setTitle(Bundle.get().getString("product"));
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelAction(ActionEvent actionEvent){
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

}
