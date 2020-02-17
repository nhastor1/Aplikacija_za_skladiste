package ba.unsa.etf.rpr.Contoller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutController {
    public ImageView imgAvatar;
    public Hyperlink linkWeb;
    public Hyperlink linkGitRep;
    public Hyperlink linkGit;

    @FXML
    public void initialize(){
        Image image = new Image("file:resources/img/avatar.jpg");
        imgAvatar.setImage(image);
    }

    public void linkWebAction(ActionEvent actionEvent){
        openURL(linkWeb.getText());
    }

    public void linkGitRepAction(ActionEvent actionEvent){
        openURL(linkGitRep.getText());
    }

    public void linkGitAction(ActionEvent actionEvent){
        openURL(linkGit.getText());
    }


    private void openURL(String url){
        try {
            java.awt.Desktop.getDesktop().browse(new URI(url));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
