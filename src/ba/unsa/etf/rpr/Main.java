package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Contoller.MainController;
import ba.unsa.etf.rpr.DAO.UserDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        if(UserDAO.getInstance().getLanguage().equals(Language.English))
            Locale.setDefault(new Locale("en","US"));
        else
            Locale.setDefault(new Locale("bs","BA"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), Bundle.get());
        MainController ctrl = new MainController(primaryStage);
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("WAFFLE");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
