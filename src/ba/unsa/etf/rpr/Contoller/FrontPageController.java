package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Exception.InvalidPasswordException;
import ba.unsa.etf.rpr.Exception.InvalidUsernameException;
import ba.unsa.etf.rpr.User.User;
import ba.unsa.etf.rpr.User.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class FrontPageController {
    UsersModel usersModel;

    public FrontPageController(UsersModel usersModel){
        this.usersModel = usersModel;
    }

    public void closeAction(ActionEvent actionEvent){
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void userAction(ActionEvent actionEvent){
        try {
            usersModel.napuni();
            UserController ctrl = new UserController(usersModel);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users.fxml"));
            loader.setController(ctrl);
            Parent root = null;

            root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Front Page");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            UsersModel finalModel = usersModel;
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
