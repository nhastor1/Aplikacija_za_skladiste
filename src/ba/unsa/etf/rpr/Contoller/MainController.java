package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.User.KorisniciModel;
import ba.unsa.etf.rpr.User.KorisnikController;
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
            KorisniciModel model = new KorisniciModel();
            model.napuni();
            KorisnikController ctrl = new KorisnikController(model);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/korisnici.fxml"));
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
