package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Contoller.MainController;
import ba.unsa.etf.rpr.DAO.MainDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(ApplicationExtension.class)
public class MainTest {
    MainDAO dao;

    @Start
    public void start (Stage stage) throws Exception {
        File dbfile = new File("database.db");
        dbfile.delete();

        dao = MainDAO.getInstance();
        MainController ctrl = new MainController(new Stage());

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), bundle);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Main");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();
    }

    @Test
    void testDatabaseExsist() {
        File dbfile = new File("database.db");
        dbfile.delete();

        {
            MainDAO dao = MainDAO.getInstance();
        }

        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            try {
                PreparedStatement korisnikUpit = conn.prepareStatement("SELECT * FROM Product");
                korisnikUpit.execute();
                conn.close();
            } catch (SQLException e) {
                fail("No table product");
            }
        } catch (SQLException e) {
            fail("No fiile or database");
        }

    }

    @Test
    void hasButton(FxRobot robot) {
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        assertNotNull(btnLogin);
    }

    @Test
    void hasField(FxRobot robot) {
        TextField fldUsername = robot.lookup("#fldUsername").queryAs(TextField.class);
        TextField fldPassword = robot.lookup("#fldPassword").queryAs(TextField.class);
        assertNotNull(fldUsername);
        assertNotNull(fldPassword);
    }

    @Test
    void canLogin(FxRobot robot) {
        robot.clickOn("#fldUsername").write("admin");
        robot.clickOn("#fldPassword").write("Admin123!");
        robot.clickOn("#btnLogin");
        Button btnUser = robot.lookup("#btnUser").queryAs(Button.class);
        assertNotNull(btnUser);
    }
}
