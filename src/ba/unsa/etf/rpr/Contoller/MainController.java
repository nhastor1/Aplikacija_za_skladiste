package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.User.UsersModel;
import ba.unsa.etf.rpr.User.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {

    public void btnAction(ActionEvent actionEvent) {
        try {
            UsersModel model = new UsersModel();
            model.napuni();
            UserController ctrl = new UserController(model);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users.fxml"));
            loader.setController(ctrl);
            Parent root = null;

            root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Korisnici");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
