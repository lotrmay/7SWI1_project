package cz.osu.carservice.controllers;

import cz.osu.carservice.models.managers.DatabaseManager;
import cz.osu.carservice.models.utils.DragWindowUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class MainFormController{

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
        try {
            Parent home_page = FXMLLoader.load(getClass().getResource("../forms/createForm.fxml"));
            Stage app = (Stage)((Node) event.getSource()).getScene().getWindow();

            DragWindowUtils.moveWindow(home_page,app);

            Scene scene = new Scene(home_page);
            scene.setFill(Color.TRANSPARENT);

            app.setScene(scene);
            app.show();
        } catch (IOException e) {
            System.err.println("Došlo k chybě při načítání formuláře createForm!");
            System.err.println(e.getMessage());
        }
    }
    @FXML
    private void showOrderListForm(ActionEvent event){

    }
}
