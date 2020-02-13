package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Exception.InvalidPasswordException;
import ba.unsa.etf.rpr.Exception.InvalidUsernameException;
import ba.unsa.etf.rpr.User.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {
    public TextField fldUsername;
    public PasswordField fldPassword;
    private UserDAO userDAO = UserDAO.getInstance();

    public void btnLoginAction(ActionEvent actionEvent) {
        try {
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
            UserController ctrl = new UserController(model);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users.fxml"));
            loader.setController(ctrl);
            Parent root = null;

            root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Korisnici");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            UsersModel finalModel = model;
            stage.setOnHiding((event)->{
                try {
                    finalModel.updateAll();
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }
            });
            stage.show();
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
