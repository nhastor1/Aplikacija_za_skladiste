package ba.unsa.etf.rpr.Contoller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ManufacturerController {
    Scene scene;
    Button btnBack;

    public ManufacturerController(Scene scene) {
        this.scene = scene;
    }

    public void backAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.setScene(scene);
    }

    public void removeAction(ActionEvent actionEvent) {
    }

    public void addAction(ActionEvent actionEvent) {
    }
}
