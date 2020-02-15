package ba.unsa.etf.rpr.Contoller;

import ba.unsa.etf.rpr.Category;
import ba.unsa.etf.rpr.DAO.CategoryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCategoryController {
    public ChoiceBox<Category> choiceSupercategory;
    public TextField fldCategory;

    @FXML
    public void initialize(){
        choiceSupercategory.setItems(CategoryDAO.getInstance().getListCategory());
    }

    public void addAction(ActionEvent actionEvent) {
        if(fldCategory.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Incorrect category");
            alert.setContentText("You need to enter field category");

            alert.showAndWait();
        }
        else{
            CategoryDAO.getInstance().addCategory(new Category(0, fldCategory.getText(), choiceSupercategory.getSelectionModel().getSelectedItem()));
            cancelAction(actionEvent);
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
