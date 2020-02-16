package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Bundle;
import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Exception.InvalidPasswordException;
import ba.unsa.etf.rpr.Exception.InvalidUsernameException;
import ba.unsa.etf.rpr.User.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mockito.cglib.core.Local;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {
    public TextField fldUsername;
    public PasswordField fldPassword;
    public Button btnLogin;
    private UserDAO userDAO = UserDAO.getInstance();
    private Stage primaryStage;
    private Scene scene;

    public MainController(Stage stage){
        primaryStage = stage;
        Locale.setDefault(new Locale("en","US"));
    }

    @FXML
    public void initialize(){
        btnLogin.setDefaultButton(true);
    }

    public void btnLoginAction(ActionEvent actionEvent) {
        try {
            scene = fldUsername.getScene();
            String username = fldUsername.getText();
            String password = fldPassword.getText();

            UsersModel model = null;
            try {
                model = new UsersModel(username, password);
            }
            catch (InvalidUsernameException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Invalid username");
                alert.setContentText("Try, again");

                alert.showAndWait();
                return;
            }
            catch (InvalidPasswordException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Invalid password");
                alert.setContentText("Try, again");

                alert.showAndWait();
                return;
            }
            model.napuni();
            FrontPageController ctrl = new FrontPageController(model, scene);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/frontPage.fxml"), Bundle.get());
            loader.setController(ctrl);
            Parent root = null;

            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle("Front Page");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            UsersModel finalModel = model;
            stage.setOnHiding((event)->{
                primaryStage = new Stage();
                primaryStage.setScene(scene);
            });
            stage.show();
            } catch (IOException e) {
            e.printStackTrace();
            }
    }
}
