package cz.osu.carservice.controllers;

import cz.osu.carservice.models.managers.DatabaseManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainFormController {
    @FXML
    public Button closeBtn;
    @FXML
    public Button createOrderBtn;
    @FXML
    public Button orderListBtn;

    private final DatabaseManager databaseManager;

    public MainFormController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    private void closeApplication(ActionEvent event){
        Platform.exit();
        System.exit(0);
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
