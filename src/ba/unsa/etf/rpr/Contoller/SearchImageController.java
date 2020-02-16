package ba.unsa.etf.rpr.Contoller;

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchFeed;
import at.mukprojects.giphy4j.exception.GiphyException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchImageController {
    public TextField fldPretraga;
    public ScrollPane scrlPane;
    public TilePane tilePane;
    public AnchorPane anchorPane;
    String imageToSet = "";
    private SearchFeed feed = null;
    List<String> url = new ArrayList<>();
    List<Button> buttons = new ArrayList<>();
    final private String loading = "/images/loading.gif";

    @FXML
    public void initialize(){
        scrlPane.setFitToWidth(true);
        tilePane.prefWidthProperty().bind(scrlPane.widthProperty());
        scrlPane.prefHeightProperty().bind(anchorPane.heightProperty());
        for(int i=0; i<25; i++) {
            Button b = new Button();
            b.setPrefHeight(128);
            b.setPrefWidth(128);
            int finalI = i;
            b.setOnAction(actionEvent1 -> imageToSet = getValidLink(url.get(finalI)));
            buttons.add(b);
        }
    }


    public void searchAction(ActionEvent actionEvent) {
        if(tilePane.getChildren().size()!=0) {
            tilePane.getChildren().clear();
            url.clear();
            feed.getDataList().clear();
            feed = null;
        }

        String search = fldPretraga.getText();

        Giphy giphy = new Giphy("dc6zaTOxFJmzC");

        try {
            feed = giphy.search(search, 25, 0);
        } catch (GiphyException e) {
            e.printStackTrace();
        }

        int size = feed.getDataList().size();

        for (int i = 0; i < size; i++)
            url.add(feed.getDataList().get(i).getImages().getOriginalStill().getUrl());

        for (int i = 0; i < size; i++) {
            setImage(i, loading);

            int finalI = i;
            Platform.runLater(() -> {
                tilePane.getChildren().add(buttons.get(finalI));
            });
        }

        for(int i=0; i<size; i++)
            setImage(i, getValidLink(url.get(i)));
    }

    public String getImageToSet() {
        return imageToSet;
    }

    public void okAction(ActionEvent actionEvent){
        if(imageToSet.isEmpty()){
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("er1"));
            alert.setHeaderText(bundle.getString("er2"));
            alert.setContentText(bundle.getString("er3"));

            alert.showAndWait();
            return;
        }

        System.out.println(imageToSet);

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void cancelAction(ActionEvent actionEvent){
        imageToSet = "";
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    private String getValidLink(String s) {
        int i = s.indexOf('.');
        int j = s.indexOf('?');

        return "https://i" + s.substring(i, j);
    }

    private void setImage(int i, String s) {
        new Thread(() -> {
            ImageView v = new ImageView(new Image(s));
            v.setFitHeight(128);
            v.setFitWidth(128);
            Platform.runLater(() -> {
                buttons.get(i).setGraphic(v);
            });
        }).start();
    }
}
