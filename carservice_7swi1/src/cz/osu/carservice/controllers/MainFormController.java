package cz.osu.carservice.controllers;

import cz.osu.carservice.models.managers.DatabaseManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.net.URI;

public class MainFormController {
    //region ElementsVariable
    @FXML
    public ImageView closeBtn;
    @FXML
    public ImageView facebookBtn;
    @FXML
    public ImageView instagramBtn;
    @FXML
    public ImageView twitterBtn;
    @FXML
    public Button createOrderBtn;
    @FXML
    public Button orderListBtn;
    @FXML
    public Label helpBtn;
    //endregion

    private final DatabaseManager databaseManager;

    public MainFormController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    private void closeApplication(MouseEvent event){
        Platform.exit();
        System.exit(0);
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
        System.out.println(databaseManager.testSelect());
    }
    @FXML
    private void showOrderListForm(ActionEvent event){
        databaseManager.testInsert("Pepa","Zelený");
    }
}
