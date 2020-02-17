package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Bundle;
import ba.unsa.etf.rpr.DAO.MainDAO;
import ba.unsa.etf.rpr.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAO.UserDAO;
import ba.unsa.etf.rpr.Enum.Language;
import ba.unsa.etf.rpr.Product;
import ba.unsa.etf.rpr.Report.PrintReport;
import ba.unsa.etf.rpr.User.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class FrontPageController {
    public Menu menuFile;
    public MenuItem menuClose;
    public Menu menuLanguage;
    public MenuItem menuBosanski;
    public MenuItem menuEngleski;
    public MenuItem menuPrint;
    public Menu menuHelp;
    public MenuItem menuAbout;
    public MenuItem menuSave;
    private UsersModel usersModel;
    private Scene scene;
    private Stage primaryStage;
    public Button btnUser;


    public FrontPageController(UsersModel usersModel, Scene scene){
        this.usersModel = usersModel;
        this.scene = scene;
    }

    @FXML
    public void initialize(){
        // Mnemonici
        menuSave.setAccelerator(
                new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)
        );

        menuClose.setAccelerator(
                new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN)
        );

        menuBosanski.setAccelerator(
                new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN)
        );

        menuEngleski.setAccelerator(
                new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN)
        );

        menuPrint.setAccelerator(
                new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN)
        );

        menuAbout.setAccelerator(
                new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN)
        );
    }

    public void closeAction(ActionEvent actionEvent){
        Stage stage = (Stage) btnUser.getScene().getWindow();
        stage.setScene(scene);
    }

    public void userAction(ActionEvent actionEvent){
        try {
            usersModel.napuni();
            UserController ctrl = new UserController(usersModel);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/users.fxml"), Bundle.get());
            loader.setController(ctrl);
            Parent root = null;

            root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(Bundle.get().getString("users"));
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

    public void manufacturerAction(ActionEvent actionEvent){
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            ManufacturerController ctrl = new ManufacturerController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manufacturer.fxml"), Bundle.get());

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle(Bundle.get().getString("manufacturers"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void warehouseAction(ActionEvent actionEvent) {
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            WarehouseController ctrl = new WarehouseController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/warehouse.fxml"), Bundle.get());

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle(Bundle.get().getString("warehouse"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void categoryAction(ActionEvent actionEvent) {
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            CategoryController ctrl = new CategoryController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"), Bundle.get());

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle(Bundle.get().getString("category"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void allProductAction(ActionEvent actionEvent) {
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            AllProductsController ctrl = new AllProductsController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/allProducts.fxml"), Bundle.get());

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle(Bundle.get().getString("allProducts"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchAction(ActionEvent actionEvent) {
        try {
            ProductSearchController ctrl = new ProductSearchController();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/searchProduct.fxml"), Bundle.get());

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(Bundle.get().getString("search"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inoviceAction(ActionEvent actionEvent) {
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            InvoiceController ctrl = new InvoiceController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/invoice.fxml"), Bundle.get());

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle(Bundle.get().getString("invoice"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void productOrederAction(ActionEvent actionEvent) {
        try {
            this.primaryStage = (Stage) btnUser.getScene().getWindow();
            Scene scene = btnUser.getScene();
            ProductOrderController ctrl = new ProductOrderController(scene);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productOrder.fxml"), Bundle.get());

            loader.setController(ctrl);
            Parent root = null;
            root = loader.load();

            Stage stage = primaryStage;
            stage.setTitle(Bundle.get().getString("productOrder"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printAction(ActionEvent actionEvent){
        try {
            new PrintReport().showReport(MainDAO.getInstance().getConn(), "/reports/Products.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void englishAction(ActionEvent actionEvent){
        System.out.println("English language");
        Locale.setDefault(new Locale("en","US"));

        UserDAO.getInstance().setLanguage(Language.English);

        restart();
    }

    public void bosanskiAction(ActionEvent actionEvent){
        System.out.println("Bosnian language");
        Locale.setDefault(new Locale("bs","BA"));

        UserDAO.getInstance().setLanguage(Language.Bosnian);

        restart();
    }

    public void aboutAction(ActionEvent actionEvent){
        System.out.println("About");
        Parent root = null;
        try {
            Stage myStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"), Bundle.get());
            AboutController ctrl = new AboutController();
            loader.setController(ctrl);
            root = loader.load();

            myStage.setTitle(Bundle.get().getString("about"));
            myStage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionSave(ActionEvent actionEvent){
        System.out.println("Save");

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(Bundle.get().getString("Dialogbox"));
        dialog.setHeaderText(Bundle.get().getString("Unesiteimedatoteke"));
        dialog.setContentText("");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> System.out.println("File name: " + name));
        
        writeInFile(result.get());
    }

    private void writeInFile(String s) {
        File file = new File(s);
        List<Product> products = ProductDAO.getInstance().getListProduct();

        PrintWriter izlaz;
        try {
            izlaz = new PrintWriter(new FileWriter(file));
            izlaz.write("id : Name : Amount : Price : WarehoseID : LocationID ::\n");
            for(Product p : products){
                izlaz.write(Integer.toString(p.getId()));
                izlaz.write(':');
                izlaz.write(p.getName());
                izlaz.write(':');
                izlaz.write(Integer.toString(p.getAmount()));
                izlaz.write(':');
                izlaz.write(Double.toString(p.getPrice()));
                izlaz.write(':');
                izlaz.write(Integer.toString(p.getWarehouse().getId()));
                izlaz.write(' ');
                izlaz.write(Integer.toString(p.getLocationOfProduction().getId()));
                izlaz.write("::\n");
            }
            izlaz.close();
        } catch (IOException e) {
            System.out.println("File " + s + " can not be opened.");
        }
    }

    private void restart(){
        Stage newStage = (Stage) btnUser.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/frontPage.fxml"), Bundle.get());
        loader.setController(this);
        try {
            newStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
