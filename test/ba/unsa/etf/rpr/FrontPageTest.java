package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Contoller.FrontPageController;
import ba.unsa.etf.rpr.Contoller.MainController;
import ba.unsa.etf.rpr.DAO.MainDAO;
import ba.unsa.etf.rpr.Exception.InvalidPasswordException;
import ba.unsa.etf.rpr.Exception.InvalidUsernameException;
import ba.unsa.etf.rpr.User.UsersModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FrontPageTest {
    MainDAO dao;

    @Start
    public void start (Stage stage) throws Exception {
        File dbfile = new File("database.db");
        dbfile.delete();

        dao = MainDAO.getInstance();
        UsersModel model = null;

        try {
            model = new UsersModel("admin", "Admin123!");
        } catch (InvalidPasswordException | InvalidUsernameException e) {
            e.printStackTrace();
        }

        FrontPageController ctrl = new FrontPageController(model, null);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/frontPage.fxml"), bundle);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Front page");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();
    }

    @Test
    void canOpen(FxRobot robot) {
        robot.clickOn("#btnManufacturer").clickOn("#btnBack");
        robot.clickOn("#btnWarehouse").clickOn("#btnBack");
        robot.clickOn("#btnCategory").clickOn("#btnBack");
        robot.clickOn("#btnInvoice").clickOn("#btnBack");
        robot.clickOn("#btnProductOrder").clickOn("#btnBack");
        robot.clickOn("#btnProduct").clickOn("#btnBack");
        robot.clickOn("#btnSearch").clickOn("#btnClose");
        robot.clickOn("#btnUser").clickOn("#btnFinish");
    }


}
