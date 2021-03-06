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
                alert.setTitle(Bundle.get().getString("WarningDialog"));
                alert.setHeaderText(Bundle.get().getString("Invalidusername"));
                alert.setContentText(Bundle.get().getString("Tryagain"));

                alert.showAndWait();
                return;
            }
            catch (InvalidPasswordException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(Bundle.get().getString("WarningDialog"));
                alert.setHeaderText(Bundle.get().getString("Invalidpassword"));
                alert.setContentText(Bundle.get().getString("Tryagain"));

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
            stage.setTitle(Bundle.get().getString("frontPage"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            UsersModel finalModel = model;
            stage.setOnHiding((event)->{
                primaryStage = new Stage();
                primaryStage.setScene(scene);
                restart();
            });
            stage.show();
            } catch (IOException e) {
            e.printStackTrace();
            }
    }

    private void restart(){
        Stage newStage = (Stage) fldUsername.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), Bundle.get());
        loader.setController(this);
        try {
            newStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
