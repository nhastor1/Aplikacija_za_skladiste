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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
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
    void hasOpened(FxRobot robot) {
        Button btnLogin = robot.lookup("#btnUser").queryAs(Button.class);
        assertNotNull(btnLogin);
    }

    @Test
    void canOpen(FxRobot robot) {
        robot.clickOn("#btnManufacturer").clickOn("#btnBack");
        robot.clickOn("#btnWarehouse").clickOn("#btnBack");
        robot.clickOn("#btnCategory").clickOn("#btnBack");
        robot.clickOn("#btnInvoice").clickOn("#btnBack");
        robot.clickOn("#btnProductOrder").clickOn("#btnBack");
        robot.clickOn("#btnProduct").clickOn("#btnBack");
        robot.clickOn("#btnSearch").clickOn("#btnCancel");
        robot.clickOn("#btnUser").clickOn("#btnKraj");
    }

    @Test
    void changeLanguage(FxRobot robot){
        robot.clickOn("#menuFile").clickOn("#menuLanguage").clickOn("#menuBosanski");
        String s = robot.lookup("#lblMainMenu").queryAs(Label.class).getText();
        assertEquals("Glavni meni", s);

        robot.clickOn("#menuFile").clickOn("#menuLanguage").type(KeyCode.DOWN).type(KeyCode.ENTER);
        s = robot.lookup("#lblMainMenu").queryAs(Label.class).getText();
        assertEquals("Main menu", s);
    }

    @Test
    void print(FxRobot robot){
        robot.clickOn("#menuFile").clickOn("#menuSave").sleep(200).write("test").clickOn();
        robot.lookup(".dialog-pane").tryQuery().isPresent();
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);
        File f = new File("test");
        assertTrue(f.exists());
    }

}
