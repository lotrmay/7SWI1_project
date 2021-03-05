package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.net.URI;

public class MainFormController extends MainController {

    @FXML
    private void closeApplication(MouseEvent event){
        super.closeApplication();
    }
    @FXML
    private void runFacebook(MouseEvent event){
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @FXML
    private void runInstagram(MouseEvent event){
        try {
            Desktop.getDesktop().browse(new URI("https://www.instagram.com/"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @FXML
    private void runTwitter(MouseEvent event){
        try {
            Desktop.getDesktop().browse(new URI("https://twitter.com/"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @FXML
    private void runHelpSite(MouseEvent event){
        try {
            Desktop.getDesktop().browse(new URI("https://seznam.cz/"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @FXML
    private void showOrderForm(ActionEvent event){
        super.setNewFormScene("createForm",event);
    }
    @FXML
    private void showOrderListForm(ActionEvent event){
        super.setNewFormScene("orderListForm",event);
    }
}
